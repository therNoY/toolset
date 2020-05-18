package pers.mihao.toolset.postman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.mihao.toolset.common.util.AuthUtil;
import pers.mihao.toolset.common.util.RespHelper;
import pers.mihao.toolset.common.vo.RespResult;
import pers.mihao.toolset.postman.dto.ReqAddHistory;
import pers.mihao.toolset.postman.dto.RespHistoryList;
import pers.mihao.toolset.postman.service.PostmanHistoryService;

import java.util.List;

@RestController
public class PostManHistoryController {

    @Autowired
    PostmanHistoryService postmanHistoryService;

    /**
     * 保存历史纪录
     * @return
     */
    @PutMapping("/api/postman/history")
    public RespResult addHistory(@RequestBody @Validated ReqAddHistory reqAddHistory, BindingResult result){
        Integer userId = AuthUtil.getAuthId();
        postmanHistoryService.addUserHistory(reqAddHistory, userId);
        return RespHelper.success();
    }

    /**
     * 获取用户的历史纪录
     * @return
     */
    @GetMapping("/api/postman/history/list")
    public RespResult getUserHistory(String filter){
        Integer userId = AuthUtil.getAuthId();
        List<RespHistoryList> histories = postmanHistoryService.getUserHistory(filter, userId);
        return RespHelper.success(histories);
    }

    /**
     * 删除用户历史
     * @param id
     * @return
     */
    @DeleteMapping("/api/postman/history")
    public RespResult delHistory(@RequestParam Integer id) {
        Integer userId = AuthUtil.getAuthId();
        if (postmanHistoryService.delHistory(userId, id)) {
            return RespHelper.success();
        }
        return RespHelper.error(50001);
    }

}
