package pers.mihao.toolset.client;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ESClient {

    public static volatile Client client = null;

    private static final String index = "music";
    private static final String type = "document";

    public static final String MUSIC = "music2";
    public static final String MUSIC163 = "music163";
    public static final String BOOK = "book";
    public static final String DOCUMENT = "document";

    private static Map<String, Client> map = new HashMap<>();

    public static Client getInstance() {
        String key = index + "_" + type;
        if (map.get(key) == null) {
            try {
                Settings esSettings = Settings.builder()
                        .put("cluster.name", "my-application") //设置ES实例的名称
                        .build();
                client = new PreBuiltTransportClient(esSettings)
                        .addTransportAddress(new TransportAddress(InetAddress.getByName("10.0.11.152"), 9300));
                map.put(key, client);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return map.get(key);

    }

    public static Client getInstance(String index, String type) {
        String key = index + "_" + type;
        if (map.get(key) == null) {
            try {
                Settings esSettings = Settings.builder()
                        .put("cluster.name", "my-application") //设置ES实例的名称
                        .build();
                client = new PreBuiltTransportClient(esSettings)
                        .addTransportAddress(new TransportAddress(InetAddress.getByName("10.0.11.152"), 9300));
                map.put(key, client);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return map.get(key);

    }

    private ESClient() {
    }

    public boolean insertDate(String documentId, JSONObject jsonObject) {
        return insertDate(documentId, jsonObject.getInnerMap());
    }

    public IndexRequestBuilder prepareIndex(Client client) {
        return client.prepareIndex(index, type);
    }

    public boolean insertDate(String documentId, Map<String, Object> source) {
        IndexRequestBuilder indexRequestBuilder = client.prepareIndex(index, type);
        indexRequestBuilder.setId(documentId);
        indexRequestBuilder.setSource(source);

        IndexResponse response = indexRequestBuilder.execute().actionGet();
        if (response != null)
            return response.getResult() == DocWriteResponse.Result.CREATED;
        else
            return false;
    }


}
