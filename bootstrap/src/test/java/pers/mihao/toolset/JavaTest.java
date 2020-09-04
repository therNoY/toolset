package pers.mihao.toolset;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public class JavaTest {
    @Test
    void name() {
        System.out.println(Math.random());
    }

    @Test
    void StringTest() {
        String a = "baidu%s.com";
        String b = String.format(a, "aa");
        System.out.println(b);
    }

    @Test
    void LocalDataTimeTest() throws InterruptedException {
        LocalDateTime localDataTime = LocalDateTime.now();
        Thread.sleep(1000);
        System.out.println(localDataTime.compareTo(LocalDateTime.now()));
    }

    @Test
    void name2() {
        for (int i = 0; i < 100; i++) {
            long index = Math.round(Math.random() * (10 - 1));
            System.out.println(index);
        }
    }

    @Test
    void name3() throws UnsupportedEncodingException {
        String content ="\u8BA1\u5212\u8C03\u5EA6\u4EFB\u52A1";

        String result = java.net.URLDecoder.decode(content.toString(), "UTF-8");
    }


    @Test
    void name4() {
        String s = "我爱他 轰轰烈烈最疯狂\n" +
                "\n" +
                "我的梦狠狠碎过却不会忘\n" +
                "\n" +
                "曾为他相信明天就是未来\n" +
                "\n" +
                "情节有多坏 都不肯醒来\n" +
                "\n" +
                "他的轻狂留在\n" +
                "\n" +
                "某一节车厢\n" +
                "\n" +
                "地下铁里的风\n" +
                "\n" +
                "比回忆还重\n" +
                "\n" +
                "整座城市一直等着我\n" +
                "\n" +
                "有一段感情还在漂泊\n" +
                "\n" +
                "对他唯一遗憾\n" +
                "\n" +
                "是分手那天\n" +
                "\n" +
                "我奔腾的眼泪\n" +
                "\n" +
                "都停不下来\n" +
                "\n" +
                "若那一刻重来 我不哭\n" +
                "\n" +
                "让他知道我可以很好\n" +
                "\n" +
                "我爱他 轰轰烈烈最疯狂\n" +
                "\n" +
                "我的梦 狠狠碎过却不会忘";

        String res = s.replace("\n\n", ";");
        System.out.println(res);
    }
}
