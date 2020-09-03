package pers.mihao.toolset.kuGouTest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.mihao.toolset.music.kugou.DTO.KuGouMusic;
import pers.mihao.toolset.music.kugou.DTO.RespKuGouMusic;
import pers.mihao.toolset.music.kugou.controller.KuGouMusicController;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KuGouSearch {

    @Autowired
    KuGouMusicController kuGouMusicController;

    @Test
    public void name() throws IOException {
        List<KuGouMusic> musics = (List<KuGouMusic>) kuGouMusicController.getMusicByName("花粥-浮白", 1, 30).getResVal();

        RespKuGouMusic respKuGouMusic = kuGouMusicController.getMusicByHash(musics.get(0).getFileHash());

        System.out.println(respKuGouMusic);
    }
}
