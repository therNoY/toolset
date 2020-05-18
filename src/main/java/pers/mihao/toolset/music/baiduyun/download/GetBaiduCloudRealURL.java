package pers.mihao.toolset.music.baiduyun.download;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.mihao.toolset.music.baiduyun.dto.Response20;
import pers.mihao.toolset.music.baiduyun.dto.SetDataBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 输入百度网盘的资源地址，获取网盘的真实下载地址。
 * 
 * @author gaoqiang
 * 
 */
public class GetBaiduCloudRealURL {
	private static Logger logger = LoggerFactory.getLogger(GetBaiduCloudRealURL.class);

	// 定义一些全局变量
	private static String url = "https://pan.baidu.com/s/1dF4EvrN?fid=766837065557701";// 资源地址
	private static String cookie = null;
	private static final String getvcodeURL = "https://pan.baidu.com/api/getvcode?prod=pan";// 请求vcode地址，不变
	private static boolean isDownload = false;//标记是否需要下载文件，false就只获取地址
	// 记录用来下载的文件属性
	private static String fileName = null;
	private static String fileSize = null;
	
	// 通过浏览器抓包分析，获取百度网盘共享文件的真实下载地址
	public static void main(String[] args) {
		if (args.length > 0) {
			/*System.out.println("输入的原始地址：" + args[0]);*/
			url = args[0];
		}

		// 1. 第一次获取cookie 连接信息 将服务端返回的cookie信息设置到放到下次发送的cookie中
		Map<String, String> respAndCookie = AnalyzeBaiduYunHttp.getRespAndCookie(url, cookie);
		logger.info("第一次请求返回的cookie: {}", respAndCookie);
		// 1.1 修改cookie 抓包看到携带了PANWEB1，不设置也没问题
		cookie = "PANWEB=1;" + respAndCookie.get("cookie").split(";")[0];

		// 2.通过返回的信息获取参数
		Map<String, String> params = getBodyParams(respAndCookie.get("body"));
		// 2.1 设置下载属性
		fileName = params.get("server_filename");
		fileSize = params.get("size");

		// 3. 发送post下载请求
		// 3.1 拼接post的url地址
		String postUrl = getPostUrl(params);
		// 3.2拼接post携带的参数
		Map<String, String> postData = getPostData(params);
		// 3.3发送post请求 得到请求结果
		String responseJson = AnalyzeBaiduYunHttp.post(postUrl, postData, cookie);

		// 4. 解析出真实下载地址
		Response20 response20 = JSON.parseObject(responseJson, Response20.class);
		String errorCode = response20.getErrno();
		int count = 0;
		while (!errorCode.equals("0") && count < 5) {
			count ++;
			if (errorCode.equals("-20")) {
				// 下载超过3次，需要验证码,获取vcode和img地址
				Map<String, String> generateValidateCode = generateValidateCode(count);
				postData.put("vcode_input", generateValidateCode.get("vcode_input"));
				postData.put("vcode_str", generateValidateCode.get("vcode_str"));
				String responseJsonCode = AnalyzeBaiduYunHttp.post(postUrl, postData, cookie);
				errorCode = JSON.parseObject(responseJsonCode, Response20.class).getErrno();
				if (errorCode.equals("0")) {
					responseJson = responseJsonCode;
				}
			}
		}

		// 5.判断是否成功获取
		if (errorCode.equals("0")) {// 成功返回真实的url
			String realURL = parseRealDownloadURL(responseJson);
			if (isDownload) {
				System.out.println("正在下载文件...");
				AnalyzeBaiduYunHttp.download(realURL, cookie, fileName, fileSize);// 进行下载
			}else{
				System.out.println("配置不下载");
			}
		} else {
			System.out.println("尝试了" + count + "次，地址获取失败");
		}

	}

	/**
	 * 拼接 POST请求的url地址
	 */
	public static String getPostUrl(Map<String, String> params) {
		StringBuffer sb1 = new StringBuffer();
		sb1.append("https://pan.baidu.com/api/sharedownload?");
		sb1.append("sign=" + params.get("sign"));
		sb1.append("&timestamp=" + params.get("timestamp"));
		sb1.append("&bdstoken=" + params.get("bdstoken"));
		sb1.append("&channel=chunlei");
		sb1.append("&clienttype=0");
		sb1.append("&web=1");
		sb1.append("&app_id=" + params.get("app_id"));
		String post_url = sb1.toString();
		return post_url;
	}

	/**
	 * 获取POST请求携带的参数
	 */
	public static Map<String, String> getPostData(Map<String, String> params) {
		// POST携带的参数(抓包可看到)
		Map<String, String> data = new HashMap<String, String>();
		data.put("encrypt", "0");
		data.put("product", "share");
		data.put("uk", params.get("uk"));
		data.put("primaryid", params.get("primaryid"));
		// 添加了[]，解码就是%5B %5D
		data.put("fid_list", "%5B" + params.get("fid_list") + "%5D");
		data.put("path_list", "");// 可以不写

		return data;
	}

	/**
	 * 从post返回数据解析出dlink字段，真实的下载地址，这个地址有效期8h
	 */
	public static String parseRealDownloadURL(String responseJson) {
		String realURL = "";
		Pattern pattern = Pattern.compile("\"dlink\":.*?,");
		Matcher matcher = pattern.matcher(responseJson);
		if (matcher.find()) {
			String tmp = matcher.group(0);
			String dlink = tmp.substring(9, tmp.length() - 2);
			realURL = dlink.replaceAll("\\\\", "");
		}
		return realURL;
	}

	/**
	 * 获取并输入验证码
	 *
	 * @return map{vcode_str:请求的vcode值, vcode_input:输入的验证码}
	 */
	public static Map<String, String> generateValidateCode(int count) {
		// 下载超过3次，需要验证码,获取vcode和img地址
		Map<String, String> vcodeResponse = AnalyzeBaiduYunHttp.getRespAndCookie(getvcodeURL, cookie);
		String res = vcodeResponse.get("body");
		Response20 response20 = JSON.parseObject(res, Response20.class);
		String vCode = response20.getVcode();
		String imgURL = response20.getImg();
		// 请求验证码
		AnalyzeBaiduYunHttp.saveImage(imgURL, cookie);
		System.out.print("查看图片，输入验证码(第" + count + "次尝试/共5次):");
		InputStreamReader is_reader = new InputStreamReader(System.in);
		Map<String, String> map = new HashMap<String, String>();
		try {
			String result = new BufferedReader(is_reader).readLine();
			System.out.println("输入的验证码为：" + result + "\n");
			map.put("vcode_str", vCode);
			map.put("vcode_input", result);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 正则匹配出json字符串，将json字符串转化为java对象。
	 */
	public static Map<String, String> getBodyParams(String body) {
		Map<String, String> map = new HashMap<String, String>();
		String setData = "";
		Pattern pattern_setData = Pattern.compile("setData.*?;");
		Matcher matcher_setData = pattern_setData.matcher(body);
		if (matcher_setData.find()) {
			String tmp = matcher_setData.group(0);
			setData = tmp.substring(8, tmp.length() - 2);
			SetDataBean bean = JSON.parseObject(setData, SetDataBean.class);
			map.put("sign", bean.getSign());
			map.put("timestamp", bean.getTimestamp());
			map.put("bdstoken", bean.getBdstoken());
			map.put("app_id", bean.getFile_list().getList()[0].getApp_id());
			map.put("uk", bean.getUk());
			map.put("shareid", bean.getShareid());
			map.put("primaryid", bean.getShareid());
			map.put("fs_id", bean.getFile_list().getList()[0].getFs_id());
			map.put("fid_list", bean.getFile_list().getList()[0].getFs_id());
			map.put("server_filename", bean.getFile_list().getList()[0].getServer_filename());
			map.put("size", bean.getFile_list().getList()[0].getSize());
		}
		return map;
	}


}






