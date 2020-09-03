package pers.mihao.toolset.music.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MyHttp {

    private static final Logger logger = LoggerFactory.getLogger(MyHttp.class);

    public static Element GetFirstElement (String url, String cssQuery) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get().getDocument();
        } catch (IOException e) {
            logger.error("Http连接错误 : {}", e);
        }
        Elements elements = document.select(cssQuery);
        if (elements.size() > 0) {
            return elements.get(0);
        }

        return null;
    }
}
