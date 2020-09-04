package pers.mihao.toolset.net.client.http.apiImpl;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.mihao.toolset.client.net.http.apiImpl.RestTemp;
import pers.mihao.toolset.net.client.http.service.HttpReqItemService;
import pers.mihao.toolset.enums.HttpMethod;


@Service
public class HttpRestTemp implements RestTemp {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpReqItemService httpReqItemService;

    public HttpResponse sendSynRequest(String url, HttpMethod method) {
        return sendSynRequest(url, method, null, null, null);
    }

    public HttpResponse sendSynRequest(String url, HttpMethod method, JSONObject requestBody) {
        return sendSynRequest(url, method, requestBody, null, null);
    }

    public HttpResponse sendSynRequest(String url, HttpMethod method, Map<String, String> cookie) {
        return sendSynRequest(url, method, null, null, cookie);
    }

    @Override
    public HttpResponse sendSynRequest(String url, HttpMethod method, JSONObject requestBody, Map<String, String> headers, Map<String, String> cookie) {

        HttpConnection httpConnection = (HttpConnection) Jsoup.connect(url);

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpConnection.header(entry.getKey(), entry.getValue());
            }
        }

        if (cookie != null && cookie.size() > 0) {
            httpConnection.cookies(cookie);
        }

        try {
            if (requestBody != null && requestBody.size() > 0)
                httpConnection.request().jsonData().putAll(requestBody.getInnerMap());
        } catch (Exception e) {
            log.error("", e);
        }

        HttpResponse response = null;
        // 执行http 请求
        long start = System.currentTimeMillis();
        log.info("请求开始执行 时间{}", start);
        log.info("请求开始执行 请求头{}", httpConnection.getRequestHeader());
        log.info("请求开始执行 请求体{}", httpConnection.request().data());
        try {
            switch (method) {
                case GET:
                    response = httpConnection.get();
                    break;
                case DELETE:
                    response = httpConnection.delete();
                    break;
                case POST:
                    response = httpConnection.post();
                    break;
                case PUT:
                    response = httpConnection.put();
                    break;
            }
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        // 构建返回的resp
        log.info("请求开始执行 请求体{}", httpConnection.request().data());
        return response;
    }

    public HttpResponse sendAsynRequest() {
        return null;
    }

}
