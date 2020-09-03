package pers.mihao.toolset.client.net.http.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.client.net.http.dto.ReqAddHistory;
import pers.mihao.toolset.client.net.http.dto.RespHistoryList;
import pers.mihao.toolset.client.net.http.service.PostmanHistoryService;
import pers.mihao.toolset.util.AuthUtil;
import pers.mihao.toolset.util.RespHelper;
import pers.mihao.toolset.vo.RespResult;

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