package pers.mihao.toolset.postman.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.mihao.toolset.postman.dto.ReqAddHistory;
import pers.mihao.toolset.postman.dto.RespHistoryList;
import pers.mihao.toolset.postman.entity.PostmanHistory;

import java.util.List;

public interface PostmanHistoryService extends IService<PostmanHistory> {

    void addUserHistory(ReqAddHistory reqAddHistory, Integer userId);

    List<RespHistoryList> getUserHistory(String filter, Integer userId);

    boolean delHistory(Integer userId, Integer id);
}
