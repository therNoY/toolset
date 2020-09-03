package pers.mihao.toolset.client.net.http.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import pers.mihao.toolset.client.net.http.dto.ReqAddHistory;
import pers.mihao.toolset.client.net.http.dto.RespHistoryList;
import pers.mihao.toolset.client.net.http.entity.PostmanHistory;

public interface PostmanHistoryService extends IService<PostmanHistory> {

    void addUserHistory(ReqAddHistory reqAddHistory, Integer userId);

    List<RespHistoryList> getUserHistory(String filter, Integer userId);

    boolean delHistory(Integer userId, Integer id);
}
