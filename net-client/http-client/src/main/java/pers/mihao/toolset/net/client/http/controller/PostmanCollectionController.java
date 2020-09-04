package pers.mihao.toolset.net.client.http.controller;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.net.client.http.consts.enums.PostmanConst;
import pers.mihao.toolset.net.client.http.dto.CollectionTreeDto;
import pers.mihao.toolset.net.client.http.dto.ReqAddCollection;
import pers.mihao.toolset.net.client.http.dto.ReqSaveItemToCollectDto;
import pers.mihao.toolset.net.client.http.entity.HttpReqCollect;
import pers.mihao.toolset.net.client.http.entity.HttpReqItem;
import pers.mihao.toolset.net.client.http.service.HttpReqService;
import pers.mihao.toolset.net.client.http.service.HttpReqItemService;
import pers.mihao.toolset.auth.AuthUtil;
import pers.mihao.toolset.util.CollectionUtil;
import pers.mihao.toolset.util.RespHelper;
import pers.mihao.toolset.dto.RespResult;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2020-02-06
 */
@RestController
public class PostmanCollectionController {

    @Autowired
    HttpReqService collectionService;

    @Autowired
    HttpReqItemService itemService;

    /**
     * 获取收藏列表
     *
     * @return
     */
    @GetMapping("/api/httpReq/collection/list")
    public RespResult getUserCollectionList() {
        Integer userId = AuthUtil.getAuthId();
        List<HttpReqCollect> collections = collectionService.getUserCollection(userId);
        List<CollectionTreeDto> resList = transferToTreeData(collections, true);
        return RespHelper.success(resList);
    }

    /**
     * 获取收藏列表
     *
     * @return
     */
    @GetMapping("/api/httpReq/noItemCollection/list")
    public RespResult getUserNoItemCollectionList() {
        Integer userId = AuthUtil.getAuthId();
        List<HttpReqCollect> collections = collectionService.getUserCollection(userId);
        List<CollectionTreeDto> resList = transferToTreeData(collections, false);
        return RespHelper.success(resList);
    }

    /**
     * 获取收藏列表
     *
     * @return
     */
    @DeleteMapping("/api/httpReq/collect/item")
    public RespResult removeItemFromCollection(@RequestParam Integer id) {
        Integer userId = AuthUtil.getAuthId();
        deleteUserItemCatch(userId);
        collectionService.removeUserCollectCatch(userId);

        HttpReqItem item = itemService.getById(Math.abs(id));

        if (item ==null || !userId.equals(item.getUserId())) {
            return RespHelper.error(50001);
        }

        if (item.getLastVersionId() != PostmanConst.defaultItemVersionId) {
            itemService.removeById(item.getLastVersionId());
        }

        itemService.removeById(Math.abs(id));

        return RespHelper.success();
    }

    /**
     * 将item保存到collection
     * @return
     */
    @PostMapping("/api/httpReq/collection/saveItemToCollect")
    public RespResult saveItemToCollect(@RequestBody ReqSaveItemToCollectDto saveItemToCollectDto) {
        Integer userId = AuthUtil.getAuthId();
        deleteUserItemCatch(userId);
        collectionService.removeUserCollectCatch(userId);
        HttpReqItem item = collectionService.saveItemToCollect(saveItemToCollectDto);
        return RespHelper.success(item);
    }

    /**
     * 将结果封装成TreeNode
     *
     * @param collections
     * @return
     */
    private List<CollectionTreeDto> transferToTreeData(List<HttpReqCollect> collections, boolean isNeedItem) {
        List<CollectionTreeDto> treeDoList = new ArrayList<>();
        for (HttpReqCollect httpReqCollect : collections) {
            CollectionTreeDto collectionTree = new CollectionTreeDto();

            collectionTree.setId(httpReqCollect.getId());
            collectionTree.setName(httpReqCollect.getName());
            collectionTree.setCollection(true);

            List<CollectionTreeDto> componentList = new ArrayList<>();

            if (CollectionUtil.isNotEmpty(httpReqCollect.getChildCollection())) {
                for (HttpReqCollect collection : httpReqCollect.getChildCollection()) {
                    CollectionTreeDto treeDto = new CollectionTreeDto();
                    treeDto.setName(collection.getName());
                    treeDto.setHistoryId(collection.getHistoryId());
                    treeDto.setId(collection.getId());
                    treeDto.setDescription(collection.getDescription());
                    treeDto.setCollection(true);
                    if (isNeedItem && CollectionUtil.isNotEmpty(collection.getChildItem())) {
                        List<CollectionTreeDto> treeDtos = new ArrayList<>();
                        for (HttpReqItem item : collection.getChildItem()) {
                            treeDtos.add(transToCollectionTree(item));
                        }
                        treeDto.setChildren(treeDtos);
                    }
                    componentList.add(treeDto);
                }
            }


            if (isNeedItem && CollectionUtil.isNotEmpty(httpReqCollect.getChildItem())) {
                for (HttpReqItem item : httpReqCollect.getChildItem()) {
                    componentList.add(transToCollectionTree(item));
                }
            }

            collectionTree.setChildren(componentList);
            treeDoList.add(collectionTree);
        }

        return treeDoList;
    }

    CollectionTreeDto transToCollectionTree(HttpReqItem item) {
        CollectionTreeDto itemTree = new CollectionTreeDto();
        itemTree.setName(item.getName());
        itemTree.setUrl(item.getUrl());
        itemTree.setHistoryId(item.getHistoryId());
        itemTree.setId(item.getId() * -1);
        itemTree.setType(item.getType());
        itemTree.setStatus(item.getStatus());
        itemTree.setCollection(false);
        return itemTree;
    }

    @PutMapping("/api/httpReq/collection")
    public RespResult addNewCollection(@RequestBody @Validated ReqAddCollection addCollection, BindingResult result) {

        HttpReqCollect collection = new HttpReqCollect();
        collection.setName(addCollection.getName());
        collection.setDescription(addCollection.getDescription());
        collection.setUserId(AuthUtil.getAuthId());

        if (addCollection.getHistoryId() == null) {
            collection.setHistoryId(-1);
        } else {
            collection.setHistoryId(addCollection.getHistoryId());
        }
        collectionService.save(collection);
        collectionService.removeUserCollectCatch(collection.getUserId());
        return RespHelper.success(collection.getId());
    }

    @DeleteMapping("api/httpReq/collect")
    public RespResult deleteUserCollect(@RequestParam Integer id) {
        HttpReqCollect collect = null;
        if ((collect = collectionService.getById(id)) != null && collect.getUserId().equals(AuthUtil.getAuthId())) {
            collectionService.deleteById(AuthUtil.getAuthId(), id);
            collectionService.removeUserCollectCatch(AuthUtil.getAuthId());
            return RespHelper.success();
        }
        return RespHelper.error(40002);
    }


    private void deleteUserItemCatch(Integer userId){
        itemService.deleteUserSaveItemCatch(userId);
        itemService.deleteUserEditCatch(userId);
    }
}
