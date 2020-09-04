package pers.mihao.toolset.net.client.http.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import pers.mihao.toolset.constant.RedisKey;
import pers.mihao.toolset.net.client.http.consts.enums.ItemStatus;
import pers.mihao.toolset.net.client.http.consts.enums.PostmanConst;
import pers.mihao.toolset.net.client.http.dto.ReqSaveItemToCollectDto;
import pers.mihao.toolset.net.client.http.entity.HttpReqCollect;
import pers.mihao.toolset.net.client.http.dao.HttpReqCollectionDao;
import pers.mihao.toolset.net.client.http.entity.HttpReqItem;
import pers.mihao.toolset.net.client.http.service.HttpReqService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.mihao.toolset.net.client.http.service.HttpReqItemService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2020-02-06
 */
@Service
public class HttpReqServiceImpl extends ServiceImpl<HttpReqCollectionDao, HttpReqCollect> implements HttpReqService {

    @Autowired
    HttpReqItemService itemService;
    @Autowired
    HttpReqCollectionDao collectionDao;

    /**
     * 获取用户收藏
     *
     * @param userId
     * @return
     */
    @Override
    @Cacheable(RedisKey.USER_HTTP_REQ_COLLECTION)
    public List<HttpReqCollect> getUserCollection(Integer userId) {

        List<HttpReqCollect> collections = collectionDao.getByUserId(userId);
        Map<Integer, HttpReqCollect> mapCollection = new HashMap<>(collections.size());
        collections.forEach(collection -> mapCollection.put(collection.getId(), collection));


        if (collections == null || collections.size() == 0) {
            return collections;
        }
        // 现获取用户所有的保存的item
        List<HttpReqItem> items = itemService.getUserSaveItem(userId);
        items.forEach(item -> {
            if (item.getCollectionsId() != null) {
                List<HttpReqItem> itemList = mapCollection.get(item.getCollectionsId()).getChildItem();
                if (itemList == null) {
                    itemList = new ArrayList<>();
                    mapCollection.get(item.getCollectionsId()).setChildItem(itemList);
                }

                itemList.add(item);
            }
        });

        List<HttpReqCollect> returnCollection = new ArrayList<>();
        collections.forEach(collection -> {
            if (collection.getHistoryId() != -1) {
                List<HttpReqCollect> childCollection = mapCollection.get(collection.getHistoryId()).getChildCollection();
                if (childCollection == null) {
                    childCollection = new ArrayList<>();
                    mapCollection.get(collection.getHistoryId()).setChildCollection(childCollection);
                }
                childCollection.add(collection);
            } else {
                returnCollection.add(collection);
            }
        });
        return returnCollection;
    }

    @Override
    @CacheEvict(RedisKey.USER_HTTP_REQ_COLLECTION)
    public void removeUserCollectCatch(Integer collection) {
    }

    @Override
    public void deleteById(Integer authId, Integer id) {

        itemService.deleteByCollectId(authId, id);
        collectionDao.deleteAllCollect(id);
    }

    @Override
    public HttpReqItem saveItemToCollect(ReqSaveItemToCollectDto saveItemToCollectDto) {
        HttpReqItem item = itemService.getById(saveItemToCollectDto.getItemId());

        if (saveItemToCollectDto.getRemove()) {
            item.setStatus(ItemStatus.SAVE);
        }else {
            item.setStatus(ItemStatus.SAVE_AND_EDIT);
        }
        item.setName(saveItemToCollectDto.getName());
        item.setCollectionsId(saveItemToCollectDto.getCollectId());


        if (item.getLastVersionId() != PostmanConst.defaultItemVersionId) {
            itemService.removeById(item.getLastVersionId());
            item.setLastVersionId(PostmanConst.defaultItemVersionId);
        }
        itemService.updateById(item);
        return item;
    }

    @Override
    public void removeItemFromCollection(Integer itemId) {


        HttpReqItem item = itemService.getById(itemId);

        if (item.getLastVersionId() != PostmanConst.defaultItemVersionId) {
            itemService.removeById(item.getLastVersionId());
        }

        itemService.removeById(itemId);

    }
}
