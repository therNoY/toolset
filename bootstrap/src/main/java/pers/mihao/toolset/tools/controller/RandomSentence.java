package pers.mihao.toolset.tools.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pers.mihao.toolset.util.IntegerUtil;
import pers.mihao.toolset.util.RespHelper;
import pers.mihao.toolset.vo.RespResult;

import java.io.IOException;

/**
 * 获取每日一句
 */
@RestController
public class RandomSentence {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;
    /* 一言的url */
    private static String randomSentenceUrl = "https://api.ooopn.com/yan/api.php?c=d";
    private static String randomImg = "https://cn.bing.com";
    private static String randomImgUrl = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=%s&n=1&mkt=zh-CN";

    @RequestMapping("/randomSentence")
    public RespResult getRandomSentence() throws IOException {
        JSONObject respJson = restTemplate.getForObject(randomSentenceUrl, JSONObject.class);
        log.info("获取一言: {}", respJson);
        return RespHelper.success(respJson);
    }

    @RequestMapping("/randomImg")
    public RespResult getRandomImg() throws IOException {

        int imgIndex = IntegerUtil.getRandomIn(1, 10);

        JSONObject respJson = restTemplate.getForObject(String.format(randomImgUrl, imgIndex), JSONObject.class);
        log.info("获取每日图片: {}", respJson);
        JSONArray imgs= respJson.getJSONArray("images");
        JSONObject imgObject = imgs.getJSONObject(0);
        String imgUrl = imgObject.getString("url");
        if (imgUrl != null) {
            imgObject.put("url", randomImg + imgUrl);
        }
        return RespHelper.success(imgObject);
    }
}
