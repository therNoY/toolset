package pers.mihao.toolset.postman.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.mihao.toolset.postman.dto.*;
import pers.mihao.toolset.postman.entity.PostmanItem;

import java.util.List;

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

    RespResponse sendHttp(PostmanItem item);

    List<PostmanItem> getUserSaveItem(Integer userId);

    void deleteByCollectId(Integer authId, Integer id);

    void deleteUserSaveItemCatch(Integer userId);

    void deleteUserEditCatch(Integer userId);

    void notSaveEditItem(String id);

    PostmanItem saveItemChange(Integer id);

    PostmanItem warpPostmanItem(PostmanItem item);

    PostmanItem saveItemChange(ReqSaveItemDto saveItemDto);
}
