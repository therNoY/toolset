package pers.mihao.toolset.client.net.http.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import pers.mihao.toolset.client.net.http.dto.ReqCollectIdDto;
import pers.mihao.toolset.client.net.http.dto.ReqSaveItem;
import pers.mihao.toolset.client.net.http.dto.ReqSaveItemDto;
import pers.mihao.toolset.client.net.http.dto.ReqUpdateItem;
import pers.mihao.toolset.client.net.http.entity.PostmanItem;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-01-05
 */
public interface PostmanItemService extends IService<PostmanItem> {

    List<PostmanItem> getUserEdit(Integer userId);

    void updateStatus(Integer id, Integer status);

    Integer saveItem(ReqSaveItem saveItem, int userId);

    PostmanItem updateItem(ReqUpdateItem updateItem);

    PostmanItem updateItemUrl(ReqUpdateItem updateItem);

    PostmanItem addNewItem(int userId, ReqCollectIdDto collectId);

    PostmanItem sendHttp(PostmanItem item, boolean addDefaultHeads);

    boolean sendHttpForRes(PostmanItem item, boolean addDefaultHeads);

    List<PostmanItem> getUserSaveItem(Integer userId);

    void deleteByCollectId(Integer authId, Integer id);

    void deleteUserSaveItemCatch(Integer userId);

    void deleteUserEditCatch(Integer userId);

    void notSaveEditItem(String id);

    PostmanItem saveItemChange(Integer id);

    PostmanItem warpPostmanItem(PostmanItem item);

    PostmanItem saveItemChange(ReqSaveItemDto saveItemDto);
}
