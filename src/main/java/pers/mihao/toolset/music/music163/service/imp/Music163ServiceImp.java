package pers.mihao.toolset.music.music163.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.mihao.toolset.common.util.HttpUtil;
import pers.mihao.toolset.music.music163.dao.Music163Dao;
import pers.mihao.toolset.music.music163.dto.Music163;
import pers.mihao.toolset.music.music163.service.Music163Service;


@Service
public class Music163ServiceImp extends ServiceImpl<Music163Dao, Music163> implements Music163Service {

    private static final String wyyRandomMusicUrl = "https://api.comments.hk/?random=";

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    Music163Dao music163Dao;

    @Override
    public Music163 getRandomMusic(double randomId) {
        String path = wyyRandomMusicUrl + randomId;
        log.info("path{}", path);
        Music163 music = null;
        try {
            music = HttpUtil.getBean(path, Music163.class);
        } catch (Exception e) {
            log.info("获取失败");
            return music;
        }
        log.info("getMap music id：{} 名称:{}", music.getSongId(), music.getAlbum());
        if (getById(music.getSongId()) == null) {
            save(music);
        }
        return music;
    }


    /**
     * 从数据库中获取数据
     * @return
     */
    @Override
    public Music163 getRandomMusicFormDb() {
        Music163 music163 = music163Dao.selectRandom();
        return music163;
    }
}
