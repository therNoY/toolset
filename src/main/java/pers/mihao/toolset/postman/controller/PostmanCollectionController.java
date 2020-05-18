package pers.mihao.toolset.postman.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import pers.mihao.toolset.common.util.AuthUtil;
import pers.mihao.toolset.common.util.CollectionUtil;
import pers.mihao.toolset.common.util.RespHelper;
import pers.mihao.toolset.common.vo.RespResult;
import pers.mihao.toolset.postman.consts.PostmanConst;
import pers.mihao.toolset.postman.dto.ReqAddCollection;
import pers.mihao.toolset.postman.dto.CollectionTreeDto;
import pers.mihao.toolset.postman.dto.ReqSaveItemToCollectDto;
import pers.mihao.toolset.postman.entity.PostmanCollect;
import pers.mihao.toolset.postman.entity.PostmanItem;
import pers.mihao.toolset.postman.service.PostmanCollectionService;
import pers.mihao.toolset.postman.service.PostmanItemService;

import java.util.ArrayList;
import java.util.List;

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
    PostmanCollectionService collectionService;

    @Autowired
    PostmanItemService itemService;

    /**
     * 获取收藏列表
     *
     * @return
     */
    @GetMapping("/api/postman/collection/list")
    public RespResult getUserCollectionList() {
        Integer userId = AuthUtil.getAuthId();
        List<PostmanCollect> collections = collectionService.getUserCollection(userId);
        List<CollectionTreeDto> resList = transferToTreeData(collections, true);
        return RespHelper.success(resList);
    }

    /**
     * 获取收藏列表
     *
     * @return
     */
    @GetMapping("/api/postman/noItemCollection/list")
    public RespResult getUserNoItemCollectionList() {
        Integer userId = AuthUtil.getAuthId();
        List<PostmanCollect> collections = collectionService.getUserCollection(userId);
        List<CollectionTreeDto> resList = transferToTreeData(collections, false);
        return RespHelper.success(resList);
    }

    /**
     * 获取收藏列表
     *
     * @return
     */
    @DeleteMapping("/api/postman/collect/item")
    public RespResult removeItemFromCollection(@RequestParam Integer id) {
        Integer userId = AuthUtil.getAuthId();
        deleteUserItemCatch(userId);
        collectionService.removeUserCollectCatch(userId);

        PostmanItem item = itemService.getById(Math.abs(id));

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
    @PostMapping("/api/postman/collection/saveItemToCollect")
    public RespResult saveItemToCollect(@RequestBody ReqSaveItemToCollectDto saveItemToCollectDto) {
        Integer userId = AuthUtil.getAuthId();
        deleteUserItemCatch(userId);
        collectionService.removeUserCollectCatch(userId);
        PostmanItem item = collectionService.saveItemToCollect(saveItemToCollectDto);
        return RespHelper.success(item);
    }

    /**
     * 将结果封装成TreeNode
     *
     * @param collections
     * @return
     */
    private List<CollectionTreeDto> transferToTreeData(List<PostmanCollect> collections, boolean isNeedItem) {
        List<CollectionTreeDto> treeDoList = new ArrayList<>();
        for (PostmanCollect postmanCollect : collections) {
            CollectionTreeDto collectionTree = new CollectionTreeDto();

            collectionTree.setId(postmanCollect.getId());
            collectionTree.setName(postmanCollect.getName());
            collectionTree.setCollection(true);

            List<CollectionTreeDto> componentList = new ArrayList<>();

            if (CollectionUtil.isNotEmpty(postmanCollect.getChildCollection())) {
                for (PostmanCollect collection : postmanCollect.getChildCollection()) {
                    CollectionTreeDto treeDto = new CollectionTreeDto();
                    treeDto.setName(collection.getName());
                    treeDto.setHistoryId(collection.getHistoryId());
                    treeDto.setId(collection.getId());
                    treeDto.setDescription(collection.getDescription());
                    treeDto.setCollection(true);
                    if (isNeedItem && CollectionUtil.isNotEmpty(collection.getChildItem())) {
                        List<CollectionTreeDto> treeDtos = new ArrayList<>();
                        for (PostmanItem item : collection.getChildItem()) {
                            treeDtos.add(transToCollectionTree(item));
                        }
                        treeDto.setChildren(treeDtos);
                    }
                    componentList.add(treeDto);
                }
            }


            if (isNeedItem && CollectionUtil.isNotEmpty(postmanCollect.getChildItem())) {
                for (PostmanItem item : postmanCollect.getChildItem()) {
                    componentList.add(transToCollectionTree(item));
                }
            }

            collectionTree.setChildren(componentList);
            treeDoList.add(collectionTree);
        }

        return treeDoList;
    }

    CollectionTreeDto transToCollectionTree(PostmanItem item) {
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

    @PutMapping("/api/postman/collection")
    public RespResult addNewCollection(@RequestBody @Validated ReqAddCollection addCollection, BindingResult result) {

        PostmanCollect collection = new PostmanCollect();
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

    @DeleteMapping("api/postman/collect")
    public RespResult deleteUserCollect(@RequestParam Integer id) {
        PostmanCollect collect = null;
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
