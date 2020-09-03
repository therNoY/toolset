package pers.mihao.toolset.music.kugou.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.InternalMax;
import org.jsoup.helper.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.enums.HttpMethod;
import pers.mihao.toolset.util.RespHelper;
import pers.mihao.toolset.vo.RespResult;
import pers.mihao.toolset.music.kugou.DTO.KuGouMusic;
import pers.mihao.toolset.music.kugou.DTO.RespKuGouMusic;
import pers.mihao.toolset.client.net.http.apiImpl.RestTemp;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pers.mihao.toolset.vo.Uri;
import pers.mihao.toolset.vo.test_dto.ESClient;

@RestController
public class KuGouMusicController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String searchUrl = "https://songsearch.kugou.com/song_search_v2";
    //    private static final String getUrl = "http://m.kugou.com/api/v1/song/get_song_info?cmd=playInfo";
    private static final String getUrl = "https://wwwapi.kugou.com/yy/index.php?r=play/getdata";
    private static final String backupGetUrl = "https://m3ws.kugou.com/api/v1/song/get_song_info?cmd=playInfo";

    private static final String KEYWORD = "keyword";
    private static final String PAGE = "page";
    private static final String PAGE_SIZE = "pagesize";
    private static final String HASH = "hash";


    private static final String COOKIE = "02e13a603af1399edf84fb2afa766da3";

    @Autowired
    RestTemp restTemp;

    @Autowired
    private TransportClient client;


    /**
     * 同于音乐名称获取音乐
     *
     * @param name
     */
    @RequestMapping("/ku_gou/music/list/search")
    public RespResult getMusicByName(String name, @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "30") int pagesize) throws IOException {
        Uri uri = new Uri(searchUrl);
        uri.addParams(KEYWORD, name)
                .addParams(PAGE, page)
                .addParams(PAGE_SIZE, pagesize)
                .addParams("platform", "WebFilter");
        HttpResponse response = restTemp.sendSynRequest(uri.getUri(), HttpMethod.GET);
        JSONObject resultJson = (JSONObject) JSON.parseObject(response.body()).get("data");
        JSONArray listArray = (JSONArray) resultJson.get("lists");
        int size = listArray.size();
        List<KuGouMusic> musicList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            JSONObject musicJson = (JSONObject) listArray.get(i);
            KuGouMusic music = JSON.parseObject(musicJson.toJSONString(), KuGouMusic.class);
            musicList.add(music);
        }
        return RespHelper.success(musicList);
    }

    @RequestMapping("/ku_gou/music/get")
    public RespKuGouMusic getMusicByHash(String hash) throws IOException {
        Uri uri = new Uri(getUrl);
        uri.addParams(HASH, hash);
        Map<String, String> cookie = new HashMap<>();
        cookie.put("kg_mid", COOKIE);
        HttpResponse response = restTemp.sendSynRequest(uri.getUri(), HttpMethod.GET, cookie);
        JSONObject jsonObject = JSON.parseObject(URLDecoder.decode(response.body(), "UTF-8"));
        RespKuGouMusic music = ((JSONObject) jsonObject.get("data")).toJavaObject(RespKuGouMusic.class);
        return music;
    }

    @RequestMapping("/ku_gou/addInfosToEs")
    public RespResult addInfosToEs(@RequestBody List<String> hashs) throws IOException {
        for (String hash : hashs) {
            addInfoToEs(hash);
        }
        return RespHelper.success();
    }

    @RequestMapping("/ku_gou/addInfoToEs")
    public RespResult addInfoToEs(String hash) throws IOException {
        Uri uri = new Uri(getUrl);
        uri.addParams(HASH, hash);
        Map<String, String> cookie = new HashMap<>();
        cookie.put("kg_mid", COOKIE);
        HttpResponse response = restTemp.sendSynRequest(uri.getUri(), HttpMethod.GET, cookie);
        JSONObject jsonObject = JSON.parseObject(URLDecoder.decode(response.body(), "UTF-8"));
        RespKuGouMusic music = ((JSONObject) jsonObject.get("data")).toJavaObject(RespKuGouMusic.class);
        handleMusicLyrics(music);
        String maxId = getMaxId(client) + 1 + "";
        Map<String, String> sourceMap = new HashMap();
        sourceMap.put("album", music.getAlbumName());
        sourceMap.put("author", music.getAuthorName());
        sourceMap.put("lyrics", music.getLyrics());
        sourceMap.put("name", music.getSongName());
        sourceMap.put("time", music.getTimelength());
        sourceMap.put("id", maxId);
        //索引值不能有大写,同一个索引，类型必须相同, 要在同一个索引名称和类型下增加多数据，索引id必须不同，否则会覆盖
        IndexResponse indexResponse = client.prepareIndex().setIndex(ESClient.MUSIC)//demo_index
                .setType(ESClient.DOCUMENT)//同一个索引，类型必须相同
                .setSource(sourceMap)
                .setId(maxId)//相同索引类型，ID必须不一样
                .execute()
                .actionGet();
        log.info("插入成功, isCreated{}", indexResponse.getResult().toString());
        return RespHelper.success();
    }




    private int getMaxId(Client client) {
        SearchRequestBuilder search = client.prepareSearch(ESClient.MUSIC).setTypes(ESClient.DOCUMENT);
        search.addAggregation(AggregationBuilders.max("maxId").field("id")).setSize(0);
        log.info("=========开始查询==========");
        log.info(search.toString());
        SearchResponse response = search.execute().actionGet();
        log.info("=========查询结束==========");
        Aggregation aggregationRes =  response.getAggregations().get("maxId");
        int id;
        return aggregationRes == null ? 0 : (id = (int) ((InternalMax) aggregationRes).value()) > 0 ? id : 0;
    }

    private void handleMusicLyrics(RespKuGouMusic music) {
        String lyrics = music.getLyrics();
        char[] chars = lyrics.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        boolean isInTime = false;
        for (int i = 0; i < lyrics.length(); i++) {
            if (!isInTime && chars[i] == '[') {
                isInTime = true;
            } else if (isInTime && chars[i] == ']') {
                isInTime = false;
            } else if (!isInTime) {
                if (chars[i] == '\n' && stringBuffer.length() > 0 && stringBuffer.charAt(stringBuffer.length() - 1) != ';') {
                    stringBuffer.append(';');
                } else if (chars[i] != '\r' && chars[i] != '\n') {
                    if (stringBuffer.length() != 0 || chars[i] != '\uFEFF') {
                        stringBuffer.append(chars[i]);
                    }
                }
            }
        }
        music.setLyrics(stringBuffer.toString());

    }

    void changUrl(RespKuGouMusic respKuGouMusic) {
        if (respKuGouMusic != null) {
            String albumImg = null;
            if ((albumImg = respKuGouMusic.getAlbumImg()) != null) {
                respKuGouMusic.setAlbumImg(removeString(albumImg));
            }
            String imgUrl = null;
            if ((imgUrl = respKuGouMusic.getImgUrl()) != null) {
                respKuGouMusic.setImgUrl(removeString(imgUrl));
            }
        }
    }

    String removeString(String oldString) {
        int start = oldString.indexOf("{");
        int end = oldString.indexOf("}");
        if (start < end && start > -1) {
            return oldString.substring(0, start) + oldString.substring(end + 2);
        }
        return null;
    }
}
