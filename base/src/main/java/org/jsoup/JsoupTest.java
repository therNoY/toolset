package org.jsoup;


import java.io.IOException;

public class JsoupTest {
    static String url = "http://to.pansoso.com/?a=to&url=rZ77gBdjtsPwguXYUH/epVV1hra7h8zgya2/1FBuvtitg/vaF3S23PCc5c1QI96lVTSGrruQzJbJ/7/NUHa%2B%2Ba2U%2B40Xe7bC8LvlpVAU3qdVSYagu9rMqcmVv/FQcA==";
    static String url2 = "https://pan.baidu.com/s/1yyX0xqEbylqJGD-Lw3gZDw";


    public static void main(String[] args) throws IOException {
        System.out.println(Jsoup.connect(url).getForLocation());
    }
}
