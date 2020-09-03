package pers.mihao.toolset.music.ntes.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.mihao.toolset.music.common.AuthGetMusic;
import pers.mihao.toolset.music.ntes.service.Music163Service;


@Component
public class AuthGetMusic163 extends AuthGetMusic{

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Music163Service music163Service;

//    @Scheduled(fixedRate = 2000)
    public void start() throws Exception {
        log.info("开始任务调度。。。");
        double randomId = Math.random();
        getMusic(()->{
            music163Service.getRandomMusic(randomId);
        });
    }
}
