package org.jsoup.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.JsonParser;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 自定义的httpResp 可以封装Document 可以封装respBody
 */
public class HttpResponse {

    private static final String COOKIE = "Set-Cookie";
    private String cookie;
    private HttpConnection.Response response;
    /* 解析出来的网页 */
    private Document document;
    /* 解析出来的String */
    private String body;
    /* 解析出来的jsonBody */
    private JSONObject jsonBody;


    public HttpResponse() {
    }

    public HttpResponse(String document) {
        this.document = Jsoup.parse(document);
    }

    public HttpResponse(String body, String cookie) {
        this.body = body;
        this.cookie = cookie;
    }

    public void setResponse(Connection.Response response) {
        this.response = (HttpConnection.Response) response;
    }

    public Document getDocument() {
        return document;
    }

    public String getCookie () {
        if (cookie != null) {
            return cookie;
        }else if (response != null){
            return response.header(COOKIE);
        }else {
            return null;
        }
    }

    public String body() {
        if (body != null) {
            return body;
        }else if (jsonBody != null) {
            return jsonBody.toString();
        }else if (document != null){
            return document.toString();
        }
        return null;
    }

    public HttpConnection.Response getResponse() {
        return response;
    }

    public void parse(Connection.Response res) throws IOException {
        Parser parser = response.req.parser();
        if (parser instanceof XmlParser) {
            this.document = res.parseXml();
        }else if (parser instanceof JsonParser) {
            this.jsonBody = res.parseJson();
        }
    }
}
