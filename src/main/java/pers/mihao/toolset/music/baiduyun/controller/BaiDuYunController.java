package pers.mihao.toolset.music.baiduyun.controller;


import com.alibaba.fastjson.JSON;
import org.jsoup.helper.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.common.util.HttpUtil;
import pers.mihao.toolset.music.baiduyun.download.AnalyzeBaiduYunHttp;
import pers.mihao.toolset.music.baiduyun.dto.Response20;
import pers.mihao.toolset.music.baiduyun.dto.SetDataBean;
import pers.mihao.toolset.music.baiduyun.dto.ValidatorDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据百度云资源 解析出真实的下载地址
 */
@RestController
public class BaiDuYunController {

    private static Logger logger = LoggerFactory.getLogger(BaiDuYunController.class);
    // 请求vcode地址，不变
    private static final String getVCodeURL = "https://pan.baidu.com/api/getvcode?prod=pan";

    public static void main(String[] args) {
        BaiDuYunController baiDuYunController = new BaiDuYunController();
        String url = "https://pan.baidu.com/s/1kVxZGZP?fid=307248557701380";
        String url2 = "https://pan.baidu.com/s/1hsghcDQ?fid=845533529949131";
        ValidatorDTO validatorDTO = baiDuYunController.getVerification(url);
        logger.info(validatorDTO.getRealDownloadURL());
    }

    /**
     * 如果不需要验证码则直接获取资源的原地址 如果需要验证码 就返回需要的验证码的地址
     * @param url
     * @return
     */
    @RequestMapping("resource/verification")
    public ValidatorDTO getVerification (String url) {
        // 得到正确的字符串
        url = url.replace("??", "&");
        ValidatorDTO validatorDTO = new ValidatorDTO();
        String cookie = null;

        // 1. 第一次获取cookie 连接信息 将服务端返回的cookie信息设置到放到下次发送的cookie中
        logger.info("发送第一次Get请求, 获取Cookie信息 和其他参数信息");
        HttpResponse response = HttpUtil.get(url, cookie);
        cookie = "PANWEB=1;" + response.getCookie().split(";")[0];
        validatorDTO.setCookie(cookie);
        // 2.通过返回的信息获取参数
        Map<String, String> resultMap = getBodyParams(response.body());

        // 3. 发送post下载请求
        // 3.1 拼接post的url地址
        String postUrl = getPostUrl(resultMap);
        validatorDTO.setPostUrl(postUrl);
        // 3.2拼接post携带的参数
        Map<String, String> postData = getPostData(resultMap);
        validatorDTO.setPostData(postData);
        // 3.3发送post请求 得到请求结果
        logger.info("第二次发送Post请求");
        String responseJson = AnalyzeBaiduYunHttp.post(postUrl, postData, cookie);
        logger.info("请求获取的信息：{}", responseJson);
        
        Response20 response20 = JSON.parseObject(responseJson, Response20.class);

        logger.info(responseJson);

        String errorCode = response20.getErrno();
        if (errorCode.equals("-20")) {
            Response20 errResp = generateValidateCode(cookie);
            validatorDTO.setResponse20(errResp);
        }else if(errorCode.equals("0")){
            String realDownloadURL = parseRealDownloadURL(responseJson);
            validatorDTO.setRealDownloadURL(realDownloadURL);
        }else {
            validatorDTO.setResponse20(response20);
        }

        return validatorDTO;
    }

    /**
     * 前端将设置好的验证码 保存好 再发送到这个地方请求获取真实地址
     * @param validatorDTO
     */
    @RequestMapping("resource/url")
    public String getRealResource (@RequestBody ValidatorDTO validatorDTO) {
        String responseJsonCode = AnalyzeBaiduYunHttp.post(validatorDTO.getPostUrl(), validatorDTO.getPostData(), validatorDTO.getCookie());
        logger.info("responseJsonCode : {}", responseJsonCode);
        String realDownloadURL = parseRealDownloadURL(responseJsonCode);
        return realDownloadURL;
    }
    /**
     * 从第一次请求中获取参数信息
     * 正则匹配出json字符串，将json字符串转化为java对象。
     */
    public Map<String, String> getBodyParams(String body) {
        Map<String, String> map = new HashMap<>();
        String setData = "";
        Pattern patternSetData = Pattern.compile("setData.*?;");
        Matcher matcherSetData = patternSetData.matcher(body);
        if (matcherSetData.find()) {
            String tmp = matcherSetData.group(0);
            setData = tmp.substring(tmp.indexOf("{"), tmp.length() - 2);
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

    /**
     * 拼接 POST请求的url地址
     */
    public String getPostUrl(Map<String, String> params) {
        StringBuffer sb1 = new StringBuffer();
        sb1.append("https://pan.baidu.com/api/sharedownload?");
        sb1.append("sign=" + params.get("sign"));
        sb1.append("&timestamp=" + params.get("timestamp"));
        sb1.append("&bdstoken=" + params.get("bdstoken"));
        sb1.append("&channel=chunlei");
        sb1.append("&clienttype=0");
        sb1.append("&web=1");
        sb1.append("&tsl=800");
        sb1.append("&app_id=" + params.get("app_id"));
        String postUrl = sb1.toString();
        return postUrl;
    }

    /**
     * 获取POST请求携带的参数
     */
    public Map<String, String> getPostData(Map<String, String> params) {
        // POST携带的参数(抓包可看到)
        Map<String, String> data = new HashMap<String, String>();
        data.put("encrypt", "0");
        data.put("product", "share");
        data.put("tsl", "800");
        data.put("uk", params.get("uk"));
        data.put("primaryid", params.get("primaryid"));
        // 添加了[]，解码就是%5B %5D
        data.put("fid_list", "%5B" + params.get("fid_list") + "%5D");
        data.put("path_list", "");// 可以不写
        return data;
    }


    /**
     * 获取验证码 信息
     * @param cookie
     * @return
     */
    public Response20 generateValidateCode(String cookie) {

        Map<String, String> vcodeResponse = AnalyzeBaiduYunHttp.getRespAndCookie(getVCodeURL, cookie);
        String res = vcodeResponse.get("body");
        Response20 response20 = JSON.parseObject(res, Response20.class);
        return response20;
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
}
