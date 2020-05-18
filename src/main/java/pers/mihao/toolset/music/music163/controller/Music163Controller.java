package pers.mihao.toolset.music.music163.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.common.util.RespHelper;
import pers.mihao.toolset.common.vo.RespResult;
import pers.mihao.toolset.music.music163.dto.Music163;
import pers.mihao.toolset.music.music163.service.Music163Service;


@RestController
public class Music163Controller {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    Music163Service music163Service;

    /**
     * 获取网易云的随机音乐
     * @return
     */
    @GetMapping("/randomMusic")
    public RespResult getWangYiYunRandomMusic(){

        Music163 music = music163Service.getRandomMusicFormDb();
        return RespHelper.success(music);
    }
}
