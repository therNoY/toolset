package pers.mihao.toolset.client.net.http.controller;

import com.alibaba.fastjson.JSON;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import pers.mihao.toolset.client.net.http.consts.enums.ItemStatus;
import pers.mihao.toolset.client.net.http.dto.PostManDetail;
import pers.mihao.toolset.client.net.http.dto.ReqCollectIdDto;
import pers.mihao.toolset.client.net.http.dto.ReqIdDto;
import pers.mihao.toolset.client.net.http.dto.ReqSaveItem;
import pers.mihao.toolset.client.net.http.dto.ReqSaveItemDto;
import pers.mihao.toolset.client.net.http.dto.ReqUpdateItem;
import pers.mihao.toolset.client.net.http.dto.ReqUpdateStatus;
import pers.mihao.toolset.client.net.http.entity.PostmanItem;
import pers.mihao.toolset.client.net.http.service.PostmanItemService;
import pers.mihao.toolset.util.AuthUtil;
import pers.mihao.toolset.util.RespHelper;
import pers.mihao.toolset.vo.RespResult;

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
        PostmanItem respResponse = itemService.sendHttp(item, true);
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
