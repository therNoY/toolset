package pers.mihao.toolset.music.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.util.RespHelper;
import pers.mihao.toolset.vo.RespResult;
import pers.mihao.toolset.music.common.dto.Music;

@RestController
public class TestController {

    @GetMapping
    public RespResult getTest (String mes) {
        return RespHelper.success(mes);
    }

    @PostMapping
    public RespResult postTest (Music music) {
        return RespHelper.success(music);
    }
}
