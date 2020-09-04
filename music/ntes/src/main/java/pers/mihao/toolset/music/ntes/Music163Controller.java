package pers.mihao.toolset.music.ntes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.util.RespHelper;
import pers.mihao.toolset.dto.RespResult;
import pers.mihao.toolset.music.ntes.dto.Music163;
import pers.mihao.toolset.music.ntes.service.Music163Service;


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
