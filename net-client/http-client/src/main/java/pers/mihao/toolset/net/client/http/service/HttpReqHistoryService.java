package pers.mihao.toolset.net.client.http.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import pers.mihao.toolset.net.client.http.dto.ReqAddHistory;
import pers.mihao.toolset.net.client.http.dto.RespHistoryList;
import pers.mihao.toolset.net.client.http.entity.HttpReqHistory;

public interface HttpReqHistoryService extends IService<HttpReqHistory> {

    void addUserHistory(ReqAddHistory reqAddHistory, Integer userId);

    List<RespHistoryList> getUserHistory(String filter, Integer userId);

    boolean delHistory(Integer userId, Integer id);
}
