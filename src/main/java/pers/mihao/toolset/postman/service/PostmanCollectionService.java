package pers.mihao.toolset.postman.service;

import pers.mihao.toolset.postman.dto.ReqSaveItemToCollectDto;
import pers.mihao.toolset.postman.entity.PostmanCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.mihao.toolset.postman.entity.PostmanItem;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-02-06
 */
public interface PostmanCollectionService extends IService<PostmanCollect> {

    List<PostmanCollect> getUserCollection(Integer userId);

    void removeUserCollectCatch(Integer collection);

    void deleteById(Integer authId, Integer id);

    PostmanItem saveItemToCollect(ReqSaveItemToCollectDto saveItemToCollectDto);

    void removeItemFromCollection(Integer userId);
}
