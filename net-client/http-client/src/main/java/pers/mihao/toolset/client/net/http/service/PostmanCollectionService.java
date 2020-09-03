package pers.mihao.toolset.client.net.http.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import pers.mihao.toolset.client.net.http.dto.ReqSaveItemToCollectDto;
import pers.mihao.toolset.client.net.http.entity.PostmanCollect;
import pers.mihao.toolset.client.net.http.entity.PostmanItem;

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
