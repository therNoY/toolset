package pers.mihao.toolset.music.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.common.util.RespHelper;
import pers.mihao.toolset.common.vo.RespResult;
import pers.mihao.toolset.music.common.dto.Music;
import pers.mihao.toolset.music.common.service.DispatchService;

import java.util.List;

@RestController
public class MusicController {

    @Autowired
    DispatchService dispatchService;

    /**
     * 通过歌名爬取音乐
     * @param musicName
     */
    @RequestMapping("/music/name/search")
    public RespResult getMusicByName (@RequestParam(defaultValue = "天下无双") String musicName) {
        List<Music> music = dispatchService.getMusicListByName(musicName);
        return RespHelper.success(music);
    }

}
