package pers.mihao.toolset.net.client.http.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import pers.mihao.toolset.net.client.http.dto.ReqSaveItemToCollectDto;
import pers.mihao.toolset.net.client.http.entity.HttpReqCollect;
import pers.mihao.toolset.net.client.http.entity.HttpReqItem;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-02-06
 */
public interface HttpReqService extends IService<HttpReqCollect> {

    List<HttpReqCollect> getUserCollection(Integer userId);

    void removeUserCollectCatch(Integer collection);

    void deleteById(Integer authId, Integer id);

    HttpReqItem saveItemToCollect(ReqSaveItemToCollectDto saveItemToCollectDto);

    void removeItemFromCollection(Integer userId);
}
