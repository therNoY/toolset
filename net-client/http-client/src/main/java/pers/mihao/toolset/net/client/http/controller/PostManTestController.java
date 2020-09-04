package pers.mihao.toolset.net.client.http.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.auth.dto.LoginDto;
import pers.mihao.toolset.net.client.http.dto.MapEntry;
import pers.mihao.toolset.util.RespHelper;
import pers.mihao.toolset.dto.RespResult;

@RestController
public class PostManTestController {

    @GetMapping("/test/get1")
    public RespResult getTest1(@RequestParam String p){
        return RespHelper.success(new MapEntry<String>("p", p));
    }

    @PostMapping("/test/post1")
    public RespResult postTest1(@RequestBody LoginDto loginDto) {
        return RespHelper.success();
    }

}
