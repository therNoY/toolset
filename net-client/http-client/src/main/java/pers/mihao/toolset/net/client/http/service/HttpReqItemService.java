package pers.mihao.toolset.net.client.http.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import pers.mihao.toolset.net.client.http.dto.ReqCollectIdDto;
import pers.mihao.toolset.net.client.http.dto.ReqSaveItem;
import pers.mihao.toolset.net.client.http.dto.ReqSaveItemDto;
import pers.mihao.toolset.net.client.http.dto.ReqUpdateItem;
import pers.mihao.toolset.net.client.http.entity.HttpReqItem;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-01-05
 */
public interface HttpReqItemService extends IService<HttpReqItem> {

    List<HttpReqItem> getUserEdit(Integer userId);

    void updateStatus(Integer id, Integer status);

    Integer saveItem(ReqSaveItem saveItem, int userId);

    HttpReqItem updateItem(ReqUpdateItem updateItem);

    HttpReqItem updateItemUrl(ReqUpdateItem updateItem);

    HttpReqItem addNewItem(int userId, ReqCollectIdDto collectId);

    HttpReqItem sendHttp(HttpReqItem item, boolean addDefaultHeads);

    boolean sendHttpForRes(HttpReqItem item, boolean addDefaultHeads);

    List<HttpReqItem> getUserSaveItem(Integer userId);

    void deleteByCollectId(Integer authId, Integer id);

    void deleteUserSaveItemCatch(Integer userId);

    void deleteUserEditCatch(Integer userId);

    void notSaveEditItem(String id);

    HttpReqItem saveItemChange(Integer id);

    HttpReqItem warpPostmanItem(HttpReqItem item);

    HttpReqItem saveItemChange(ReqSaveItemDto saveItemDto);
}
