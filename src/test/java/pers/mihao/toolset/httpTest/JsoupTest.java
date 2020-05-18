package pers.mihao.toolset.httpTest;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.mihao.toolset.common.util.HttpUtil;
import pers.mihao.toolset.music.music163.dto.Music163;

import java.io.IOException;

public class JsoupTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String wyyRandomMusicUrl = "https://api.comments.hk/?random=";

    /**
     * 获取网易云的随机音乐
     *
     * @return
     */
    @Test
    public void getWangYiYunRandomMusic() throws IOException {
        double randomId = Math.random();
        Music163 music = HttpUtil.getBean(wyyRandomMusicUrl + randomId, Music163.class);
        System.out.println(music);
    }


    @Test
    void getJson() throws IOException {
        String url = "http://127.0.0.1:8088/randomImg";
        HttpResponse httpResponse = null;
        Connection connection = Jsoup.connect(url);
        httpResponse = connection.get();
        System.out.println(httpResponse.body());
    }

    @Test
    void getString() throws IOException {
        String url = "http://127.0.0.1:8088/hello";
        HttpResponse httpResponse = null;
        Connection connection = Jsoup.connect(url);
        httpResponse = connection.get();
        System.out.println(httpResponse.body());
    }

    @Test
    void getHTML() throws IOException {
        String url = "https://www.baidu.com/";
        HttpResponse httpResponse = null;
        Connection connection = Jsoup.connect(url);
        httpResponse = connection.get();
        System.out.println(httpResponse.body());
    }
}
