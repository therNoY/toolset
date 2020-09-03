package pers.mihao.toolset.client.net.http.apiImpl;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import org.jsoup.helper.HttpResponse;
import pers.mihao.toolset.enums.HttpMethod;

/**
 * @Author mh32736
 * @Date 2020/9/3 18:25
 */
public interface RestTemp {

    HttpResponse sendSynRequest(String url, HttpMethod method);

    HttpResponse sendSynRequest(String url, HttpMethod method, JSONObject requestBody);

    HttpResponse sendSynRequest(String url, HttpMethod method, Map<String, String> cookie);

    HttpResponse sendSynRequest(String url, HttpMethod method, JSONObject requestBody, Map<String, String> headers, Map<String, String> cookie);

}
