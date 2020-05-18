package pers.mihao.toolset.postman.controller;

import org.springframework.web.bind.annotation.*;
import pers.mihao.toolset.auth.dto.LoginDto;
import pers.mihao.toolset.common.util.RespHelper;
import pers.mihao.toolset.common.vo.RespResult;
import pers.mihao.toolset.postman.dto.MapEntry;

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
