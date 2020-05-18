package pers.mihao.toolset.postman.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.mihao.toolset.common.util.AuthUtil;
import pers.mihao.toolset.common.util.RespHelper;
import pers.mihao.toolset.common.vo.RespResult;
import pers.mihao.toolset.postman.consts.ItemStatus;
import pers.mihao.toolset.postman.dto.*;
import pers.mihao.toolset.postman.entity.PostmanItem;
import pers.mihao.toolset.postman.service.PostmanItemService;

import java.util.List;

@RestController
public class PostManItemController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PostmanItemService itemService;

    /**
     * 获取用户的编辑记录
     * @return
     */
    @GetMapping("/api/postman/edit/list")
    public RespResult getUserEdit(){
        Integer userId = AuthUtil.getAuthId();
        List<PostmanItem> histories = itemService.getUserEdit(userId);
        return RespHelper.success(histories);
    }

    /**
     * 点击历史添加到正在编辑的列表中
     */
    @PutMapping("/api/postman/item")
    public RespResult addItem(@RequestBody @Validated ReqSaveItem saveItem, BindingResult result){
        int userId = AuthUtil.getAuthId();
        itemService.deleteUserEditCatch(userId);

        Integer i = itemService.saveItem(saveItem, userId);
        return RespHelper.success(i);
    }


    /**
     * 创建一个新的文件夹中的item
     * @return
     */
    @PutMapping("/api/postman/item/empty")
    public RespResult addNewItem(@RequestBody ReqCollectIdDto collectId){
        int userId = AuthUtil.getAuthId();
        deleteUserItemCatch(userId);
        PostmanItem addNewItem = itemService.addNewItem(userId, collectId);
        return RespHelper.success(addNewItem);
    }

    /**
     * 保存更改的列表
     */
    @PostMapping("/api/postman/item")
    public RespResult updateItem(@RequestBody ReqUpdateItem updateItem){
        int userId = AuthUtil.getAuthId();
        if (updateItem.getUserId() != userId) {
            return RespHelper.error(50001);
        }
        deleteUserItemCatch(userId);
        return RespHelper.success(itemService.updateItem(updateItem));
    }

    /**
     * 保存更改的列表
     */
    @PostMapping("/api/postman/item/save")
    public RespResult saveItem(@RequestBody ReqSaveItemDto saveItemDto){
        int userId = AuthUtil.getAuthId();
        deleteUserItemCatch(userId);

        if (saveItemDto.getRemove()) {
            return RespHelper.success(itemService.saveItemChange(saveItemDto.getId()));
        }else {
            return RespHelper.success(itemService.saveItemChange(saveItemDto));
        }
    }

    /**
     * 保存更改的URL
     */
    @PostMapping("/api/postman/item/url")
    public RespResult updateItemUrl(@RequestBody ReqUpdateItem updateItem){
        int userId = AuthUtil.getAuthId();
        if (updateItem.getUserId() != userId) {
            return RespHelper.error(50001);
        }
        deleteUserItemCatch(userId);
        return RespHelper.success(itemService.updateItemUrl(updateItem));
    }

    /**
     * 删除一个item
     * @param id
     * @return
     */
    @DeleteMapping("/api/postman/item")
    public RespResult notSaveEditItem(@RequestParam String id){
        deleteUserItemCatch(AuthUtil.getAuthId());

        itemService.notSaveEditItem(id);
        return RespHelper.success();
    }
    /**
     * 更新item 状态
     *
     */
    @PostMapping("/api/postman/item/status")
    public RespResult updateItem(@RequestBody @Validated ReqUpdateStatus reqUpdateStatus, BindingResult result){
        int userId = AuthUtil.getAuthId();

        deleteUserItemCatch(userId);

        PostmanItem item = itemService.getById(Math.abs(reqUpdateStatus.getId()));
        if (item == null || item.getUserId() != userId) {
            return RespHelper.error(50001);
        }
        itemService.updateStatus(Math.abs(reqUpdateStatus.getId()), reqUpdateStatus.getStatus());
        item.setStatus(ItemStatus.SAVE_AND_EDIT);
        return RespHelper.success(itemService.warpPostmanItem(item));
    }

    @PostMapping("/api/postman/sendHttp")
    public RespResult getHttpSendRes(@RequestBody @Validated ReqIdDto reqIdDto, BindingResult bindingResult){
        PostmanItem item = itemService.getById(reqIdDto.getId());
        PostManDetail detail = JSON.parseObject(item.getDetail(), PostManDetail.class);
        item.setPostManDetail(detail);
        RespResponse respResponse = itemService.sendHttp(item);
        if (respResponse != null) {
            return RespHelper.success(respResponse);
        }
        return RespHelper.error(500);
    }


    private void deleteUserItemCatch(Integer userId){
        itemService.deleteUserSaveItemCatch(userId);
        itemService.deleteUserEditCatch(userId);
    }

}
