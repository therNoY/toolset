package pers.mihao.toolset.music.seed.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.music.seed.dto.ResourceDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 利用盘搜搜 模拟HTTP请求获取百度云资源
 */
@RestController
public class SourceController {

    private static Logger log = LoggerFactory.getLogger(SourceController.class);
    // 盘搜搜的网站URL
    private static final String socket = "https://www.pansoso.la";

    /**
     * 从盘搜搜获取 10 个资源的链接
     * @param name 搜索名字
     * @return
     * @throws IOException
     */
    @RequestMapping("source/list")
    public List<ResourceDTO> findResourceListByName (String name) throws IOException {
        // 1.从盘搜搜搜索十个资源目录
        String url = socket + "/zh/" + name; // PSS 搜素资源的URL
        Document firstDocument = Jsoup.connect(url).get().getDocument();
        Elements divElements = firstDocument.select("div.pss");
        List<ResourceDTO> resourceDTOList = new ArrayList<>();
        for (int i = 0; i <divElements.size() ; i++) {
            Element divElement = divElements.get(i);
            Elements aElements = divElement.select("a");
            if (aElements.size() > 0) {
                String resourceUrl = socket + aElements.get(0).attr("href");
                String resourceName = aElements.get(0).text();
                ResourceDTO resourceDTO = new ResourceDTO(resourceUrl, resourceName);
                resourceDTOList.add(resourceDTO);
            }
        }
        return resourceDTOList;



    }

    /**
     * 根据盘搜搜的资源连接解析出 百度云的资源链接
     * @param url  盘搜搜的资源url
     * @return
     * @throws IOException
     */
    @RequestMapping("source/realUrl")
    public ResourceDTO getRealUrl (@RequestParam String url) throws IOException {
        ResourceDTO resourceDTO = new ResourceDTO(url, "");
        Document secondDocument = Jsoup.connect(resourceDTO.getUrl()).get().getDocument();
        Elements elements = secondDocument.select("div.down a[rel]");
        if (elements.size() > 0) {
            String message = elements.get(0).attr("href");
            Document thirdDocument = Jsoup.connect(message).get().getDocument();
            // 获取连接
            Elements elements1 = thirdDocument.select("a.btn-download");
            if (elements1.size() > 0) {
                String realUrl = elements1.get(0).attr("href");
                String key = elements1.get(0).attr("href");
                if (realUrl.length() > 0) {
                    resourceDTO.setUrl(getLocation(realUrl));
                }
            }
            // 获取获取码
            Elements codeElement = thirdDocument.select("div.file");
            if (codeElement.size() == 2) {
                String codeString = codeElement.get(1).text();
                if (codeString.trim().contains("提取码")) {
                    String[] strings = codeString.trim().split("：");
                    if (strings.length == 3) {
                        resourceDTO.setKey(strings[2]);
                    }
                }
            }
        }
        return resourceDTO;
    }


    private String getLocation (String url) throws IOException {
        String location = Jsoup.connect(url).getForLocation();
        return location;
    }
}
