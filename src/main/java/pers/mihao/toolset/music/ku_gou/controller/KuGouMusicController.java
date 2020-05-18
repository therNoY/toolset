package pers.mihao.toolset.music.ku_gou.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.music.common.dto.Uri;
import pers.mihao.toolset.music.ku_gou.DTO.KuGouMusic;
import pers.mihao.toolset.music.ku_gou.DTO.RespKuGouMusic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class KuGouMusicController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String searchUrl = "https://songsearch.kugou.com/song_search_v2";
    private static final String getUrl = "http://m.kugou.com/api/v1/song/get_song_info?cmd=playInfo";
    private static final String backupGetUrl = "https://m3ws.kugou.com/api/v1/song/get_song_info?cmd=playInfo";

    private static final String KEYWORD = "keyword";
    private static final String PAGE = "page";
    private static final String PAGE_SIZE = "pagesize";
    private static final String HASH = "hash";


    /**
     * 同于音乐名称获取音乐
     * @param name
     */
    @RequestMapping("/ku_gou/music/list/search")
    public List<KuGouMusic> getMusicByName (String name, @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "30")int pagesize) throws IOException {
        Uri uri = new Uri(searchUrl);
        uri.addParams(KEYWORD, name).addParams(PAGE, page).addParams(PAGE_SIZE, pagesize);
        HttpResponse response = Jsoup.connect(uri.getUri()).get();
        JSONObject resultJson = (JSONObject) JSON.parseObject(response.body()).get("data");
        JSONArray listArray = (JSONArray) resultJson.get("lists");
        int size = listArray.size();
        List<KuGouMusic> musicList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            JSONObject musicJson = (JSONObject) listArray.get(i);
            KuGouMusic music = JSON.parseObject(musicJson.toJSONString(), KuGouMusic.class);
            musicList.add(music);
        }
        return musicList;
    }

    @RequestMapping ("/ku_gou/music/get")
    public RespKuGouMusic getMusicByHash (String hash) throws IOException {
        Uri uri = new Uri(getUrl);
        uri.addParams(HASH, hash);
        HttpResponse response = Jsoup.connect(uri.getUri()).get();
        RespKuGouMusic respKuGouMusic = JSON.parseObject(response.body(), RespKuGouMusic.class);
        changUrl(respKuGouMusic);
        return respKuGouMusic;
    }

    void changUrl (RespKuGouMusic respKuGouMusic) {
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

    String removeString (String oldString) {
        int start = oldString.indexOf("{");
        int end = oldString.indexOf("}");
        if (start < end && start > -1) {
            return oldString.substring(0, start) + oldString.substring(end + 2);
        }
        return null;
    }
}
