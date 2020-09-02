package pers.mihao.toolset.postman.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import pers.mihao.toolset.common.annotation.KnowledgePoint;
import pers.mihao.toolset.common.constant.RedisKey;
import pers.mihao.toolset.common.util.AuthUtil;
import pers.mihao.toolset.common.util.EnumUtil;
import pers.mihao.toolset.common.vo.MyException;
import pers.mihao.toolset.postman.consts.ItemStatus;
import pers.mihao.toolset.postman.consts.PostmanConst;
import pers.mihao.toolset.postman.consts.enums.HttpMethod;
import pers.mihao.toolset.postman.dao.PostmanItemDao;
import pers.mihao.toolset.postman.dto.*;
import pers.mihao.toolset.postman.entity.CommonHeader;
import pers.mihao.toolset.postman.entity.PostmanHistory;
import pers.mihao.toolset.postman.entity.PostmanItem;
import pers.mihao.toolset.postman.dto.PostManDetail.RequestEntry;
import pers.mihao.toolset.postman.service.CommonHeaderService;
import pers.mihao.toolset.postman.service.PostmanCollectionService;
import pers.mihao.toolset.postman.service.PostmanHistoryService;
import pers.mihao.toolset.postman.service.PostmanItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.*;

import static pers.mihao.toolset.postman.consts.PostmanConst.defaultItemVersionId;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2020-01-05
 */
@Service
public class PostmanItemServiceImpl extends ServiceImpl<PostmanItemDao, PostmanItem> implements PostmanItemService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PostmanItemDao postmanItemDao;
    @Autowired
    CommonHeaderService commonHeaderService;
    @Autowired
    PostmanHistoryService postmanHistoryService;
    @Autowired
    PostmanCollectionService collectionService;

    /**
     * 获取用户的编辑记录
     *
     * @param userId
     * @return
     */
    @Override
    @Cacheable(RedisKey.USER_POSTMAN_EDIT)
    public List<PostmanItem> getUserEdit(Integer userId) {
        List<PostmanItem> editList = postmanItemDao.getUserItemByStatus(userId, ItemStatus.EDIT, ItemStatus.SAVE_AND_EDIT);
        editList.forEach(postmanItem -> warpPostmanItem(postmanItem));
        return editList;
    }

    /**
     * 更新item状态
     *
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Integer id, Integer status) {
        postmanItemDao.updateStatus(id, status);
    }

    /**
     * 保存item
     *
     * @param saveItem
     * @param userId
     */
    @Override
    public Integer saveItem(ReqSaveItem saveItem, int userId) {
        PostmanItem postmanItem = initEmptyItem(userId);
        postmanItem.setUrl(saveItem.getLabel());
        postmanItem.setDetail(saveItem.getPostManDetail().toJSONString());
        postmanItem.setType(saveItem.getType());
        postmanItem.setHistoryId(saveItem.getHistoryId());
        postmanItemDao.insert(postmanItem);
        return postmanItem.getId();
    }

    /**
     * 更新item 主要是更新参数
     *
     * @param updateItem
     * @return
     */
    @KnowledgePoint("将Integer 与 int比较的时候如果Integer 对象是空会空指针")
    @Override
    public PostmanItem updateItem(ReqUpdateItem updateItem) {
        updateItem.setDetail(JSON.toJSONString(updateItem.getPostManDetail()));
        if (!PostmanConst.emptyUrl.equals(updateItem.getLabel()) && updateItem.getLabel() != null) {
            updateItem.setUrl(getUrl(updateItem));
        } else {
            updateItem.setUrl(updateItem.getLabel());
        }


        // 如果现在是最开始的版本就需要备份
        if (updateItem.getLastVersionId() == null || updateItem.getLastVersionId() == defaultItemVersionId) {
            PostmanItem item = getById(updateItem.getId());
            item.setId(null);
            item.setStatus(ItemStatus.EDIT_HISTORY);
            save(item);
            updateItem.setLastVersionId(item.getId());
        }

        postmanItemDao.updateById(updateItem);

        PostmanItem item;
        if ((item = postmanItemDao.selectById(updateItem.getId())) != null) {
            if (item.getStatus().equals(ItemStatus.SAVE_AND_EDIT) && !updateItem.getName().equals(item.getName())){
                collectionService.removeUserCollectCatch(AuthUtil.getAuthId());
            }
        }
        return updateItem;
    }

    /**
     * 更新url
     *
     * @param updateItem
     * @return
     */
    @Override
    public PostmanItem updateItemUrl(ReqUpdateItem updateItem) {
        String[] splits = updateItem.getLabel().split("\\?");
        List<PostManDetail.RequestEntry> nowList = new ArrayList<>();
        updateItem.getPostManDetail().getParams().forEach(entry -> {
            if (!entry.getS()) {
                nowList.add(entry);
            }
        });
        // 目录中含有参数
        if (splits.length > 1) {
            List<String> pars = Arrays.asList(splits[1].split("&"));
            pars.forEach(s -> {
                PostManDetail.RequestEntry entry = new PostManDetail.RequestEntry();
                String[] pas = s.split("=");
                if (pas.length > 1) {
                    entry.setK(pas[0]);
                    entry.setV(pas[1]);
                } else {
                    entry.setK(pas[0]);
                    entry.setV("");
                }
                entry.setS(true);
                entry.setD("");
                nowList.add(entry);
            });
        }

        updateItem.setUrl(updateItem.getLabel());
        updateItem.getPostManDetail().setParams(nowList);
        updateItem.setDetail(JSON.toJSONString(updateItem.getPostManDetail()));

        // 如果现在是最开始的版本就需要备份
        if (updateItem.getLastVersionId() != null && updateItem.getLastVersionId() == defaultItemVersionId) {
            PostmanItem item = getById(updateItem.getId());
            item.setId(null);
            item.setStatus(ItemStatus.EDIT_HISTORY);
            save(item);
            updateItem.setLastVersionId(item.getId());
        }
        postmanItemDao.updateById(updateItem);
        return updateItem;
    }


    /**
     * 创建一个新的
     *
     * @param userId
     * @param collectId
     * @return
     */
    @Override
    public PostmanItem addNewItem(int userId, ReqCollectIdDto collectId) {
        PostmanItem postmanItem = initEmptyItem(userId);
        if (collectId != null && collectId.getCollectId() != null) {
            postmanItem.setStatus(ItemStatus.SAVE_AND_EDIT);
            postmanItem.setCollectionsId(collectId.getCollectId());
            postmanItem.setName(collectId.getName());
            collectionService.removeUserCollectCatch(userId);
        }
        postmanItemDao.insert(postmanItem);
        return postmanItem;
    }

    private PostmanItem initEmptyItem(int userId) {
        PostmanItem postmanItem = new PostmanItem();
        postmanItem.setUserId(userId);
        postmanItem.setStatus(ItemStatus.EDIT);
        postmanItem.setUrl(PostmanConst.emptyUrl);
        postmanItem.setName(PostmanConst.emptyName);
        postmanItem.setType(HttpMethod.GET.type());
        postmanItem.setCreateDate(LocalDateTime.now());
        PostManDetail postManDetail = new PostManDetail();
        postManDetail.setParams(new ArrayList<>());
        postManDetail.setAuth(new MapEntry<>());
        postManDetail.setBody("");
        postManDetail.setHeaders(new ArrayList<>());
        postmanItem.setDetail(JSONObject.toJSONString(postManDetail));
        postmanItem.setPostManDetail(postManDetail);
        return postmanItem;
    }


    /**
     * 发送http请求
     *
     * @param item
     * @param addDefaultHeads
     * @return
     */
    @Override
    public PostmanItem sendHttp(PostmanItem item, boolean addDefaultHeads) {
        if (sendHttpForRes(item, addDefaultHeads)) return null;
        // 增加历史纪录
        PostmanHistory history = new PostmanHistory();
        history.setUrl(item.getUrl());
        history.setUserId(AuthUtil.getAuthId());
        history.setType(item.getType());
        history.setCreateDate(LocalDateTime.now());
        history.setDetail(JSON.toJSONString(item.getPostManDetail()));
        postmanHistoryService.save(history);

        updateById(item);
        item.setLabel(item.getUrl());
        return item;
    }

    /**
     * 发送http
     * @param item
     * @param addDefaultHeads
     * @return
     */
    public boolean sendHttpForRes(PostmanItem item, boolean addDefaultHeads) {
        log.info("准备发送http请求{}", item);

        HttpConnection httpConnection = (HttpConnection) Jsoup.connect(item.getUrl());
        List<CommonHeader> commonHeaders = commonHeaderService.getCommonHeader().getCommonHeaderList();

        // 设置请求头
        List<RequestEntry> entries = item.getPostManDetail().getHeaders();
        for (RequestEntry entry : entries) {
            if (entry.getS()) {
                httpConnection.header(entry.getK(), entry.getV());
            }
        }

        //设置默认请求头
        List<MapEntry<String>> addDefaultHeaders = new ArrayList<>();
        if (addDefaultHeads) {
            for (CommonHeader commonHeader : commonHeaders) {
                if (commonHeader.getDefaultUse().equals(1) && !httpConnection.request().hasHeader(commonHeader.getKey())) {
                    httpConnection.header(commonHeader.getKey(), commonHeader.getDefaultValue());
                    addDefaultHeaders.add(new MapEntry<>(commonHeader.getKey(), commonHeader.getDefaultValue()));
                }
            }
        }

        // 设置auth
        MapEntry<String> authKey = item.getPostManDetail().getAuth();
        if (authKey.getV() != null) {
            if (authKey.getK().equals("Bearer Token")) {
                httpConnection.header(HttpConnection.AuthKey, "Bearer " + authKey.getV());
            }
        }



        try {
            String body = item.getPostManDetail().getBody();
            JSONObject jsonObject = (JSONObject) JSON.parse(body);
            if (jsonObject != null && jsonObject.size() > 0)
               httpConnection.request().jsonData().putAll(jsonObject.getInnerMap());
        } catch (Exception e) {
            log.error("", e);
            return true;
        }

        HttpResponse response = null;
        // 执行http 请求
        HttpMethod httpMethod = EnumUtil.valueOf(HttpMethod.class, item.getType());

        long start = System.currentTimeMillis();
        log.info("请求开始执行 时间{}", start);
        log.info("请求开始执行 请求头{}", httpConnection.getRequestHeader());
        log.info("请求开始执行 请求体{}", httpConnection.request().data());
        try {
            switch (httpMethod) {
                case GET:
                    response = httpConnection.get();
                    break;
                case DELETE:
                    response = httpConnection.delete();
                    break;
                case POST:
                    response = httpConnection.post();
                    break;
                case PUT:
                    response = httpConnection.put();
                    break;
            }
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        // 构建返回的resp
        if (response == null) {
            return true;
        }
        HttpConnection.Response res = response.getResponse();
        item.setStatusCode(res.statusCode());
        item.setStatsMes(res.statusMessage());
        item.setTimer(end - start);
        item.setResBody(response.body());
        item.setRespBody(response.body());

        List<MapEntry<String>> headers = new ArrayList<>();
        Map<String, List<String>> allHeaders = res.allHeaders();

        allHeaders.forEach((k, vs) -> {
            for (String v : vs) {
                MapEntry<String> mapEntry = new MapEntry<>();
                mapEntry.setK(k);
                mapEntry.setV(v);
                headers.add(mapEntry);
            }
        });

        item.setHeaders(JSON.toJSONString(headers));
        item.setRespHeaders(headers);

        List<MapEntry<String>> mapEntries = MapEntry.mapToMapEntryList(res.cookies());
        item.setCookies(JSON.toJSONString(mapEntries));
        item.setRespCookies(mapEntries);
        item.setContentType(res.contentType() == null ? "text/html" : res.contentType());

        if (addDefaultHeads) {
            item.setRespAddHeaders(addDefaultHeaders);
            item.setAddHeaders(JSON.toJSONString(addDefaultHeaders));
        }
        return false;
    }

    @Override
    @Cacheable(RedisKey.USER_POSTMAN_SAVE_ITEM)
    public List<PostmanItem> getUserSaveItem(Integer userId) {
        List<PostmanItem> editList = postmanItemDao.getUserItemByStatus(userId, ItemStatus.SAVE, ItemStatus.SAVE_AND_EDIT);
        return editList;
    }


    @Override
    public void deleteByCollectId(Integer authId, Integer id) {
        postmanItemDao.deleteByCollectId(authId, id);
    }

    @Override
    @CacheEvict(RedisKey.USER_POSTMAN_SAVE_ITEM)
    public void deleteUserSaveItemCatch(Integer userId) {
    }


    @Override
    @CacheEvict(RedisKey.USER_POSTMAN_EDIT)
    public void deleteUserEditCatch(Integer userId) {
    }

    /**
     *
     * @param id
     */
    @Override
    public void notSaveEditItem(String id) {
        PostmanItem item = getById(id);

        switch (item.getStatus()) {
            case ItemStatus.SAVE_AND_EDIT:
                if (item.getLastVersionId() == defaultItemVersionId) {
                    // 直接修改状态从编辑编辑状态移除
                    updateStatus(item.getId(), ItemStatus.SAVE);
                } else {
                    // 回退状态 从编辑状态移到保存状态
                    rollbackItem(item);
                }
                break;
            case ItemStatus.EDIT:
                if (item.getLastVersionId() != defaultItemVersionId) {
                    removeById(item.getLastVersionId());
                }
                removeById(id);
                break;
            default:
                throw new MyException("错误的item状态");
        }
    }

    /**
     * 保存item
     * @param id
     * @return
     */
    @Override
    public PostmanItem saveItemChange(Integer id) {
        PostmanItem postmanItem = getById(id);
        int versionId;
        if ((versionId = postmanItem.getLastVersionId()) != defaultItemVersionId) {
            postmanItem.setStatus(ItemStatus.SAVE);
            postmanItem.setLastVersionId(-1);
            updateById(postmanItem);
            removeById(versionId);
            return postmanItem;
        }
        return null;
    }

    private void rollbackItem(PostmanItem item) {
        removeById(item.getLastVersionId());
        item.setStatus(ItemStatus.SAVE);
        item.setLastVersionId(defaultItemVersionId);
        updateById(item);
    }

    /**
     * @param postmanItem
     * @return
     */
    public String getUrl(PostmanItem postmanItem) {
        StringBuilder url = new StringBuilder(postmanItem.getLabel().split("\\?")[0]);
        if (postmanItem.getPostManDetail() != null && postmanItem.getPostManDetail().getParams() != null) {
            url.append("?");
            List<PostManDetail.RequestEntry> entries = postmanItem.getPostManDetail().getParams();
            for (int i = 0; i < entries.size(); i++) {
                PostManDetail.RequestEntry entry = entries.get(i);
                if (entry.getS() && entry.getK().length() > 0) {
                    url.append(entry.getK()).append("=").append(entry.getV()).append("&");
                }
            }
            return url.toString().substring(0, url.length() - 1);
        }
        return url.toString();
    }

    @Override
    public PostmanItem saveItemChange(ReqSaveItemDto saveItemDto) {
        PostmanItem postmanItem = getById(saveItemDto.getId());
        int versionId;
        if ((versionId = postmanItem.getLastVersionId()) != defaultItemVersionId) {
            postmanItem.setStatus(ItemStatus.SAVE_AND_EDIT);
            postmanItem.setLastVersionId(-1);
            updateById(postmanItem);
            removeById(versionId);
            return warpPostmanItem(postmanItem);
        }
        return null;
    }

    public PostmanItem warpPostmanItem(PostmanItem item) {
        item.setLabel(item.getUrl());

        if (item.getDetail() != null){
            item.setPostManDetail(JSON.parseObject(item.getDetail(), PostManDetail.class));
        }

        if (item.getCookies() != null)
            item.setRespCookies(JSONObject.parseArray(item.getCookies(), MapEntry.class));

        if (item.getResBody() != null) {
            item.setRespBody(JSONObject.parseObject(item.getResBody()));
        }

        if (item.getAddHeaders() != null) {
            item.setRespAddHeaders(JSONObject.parseArray(item.getCookies(), MapEntry.class));
        }

        if (item.getHeaders() != null) {
            item.setRespHeaders(JSONObject.parseArray(item.getCookies(), MapEntry.class));
        }

        return item;
    }
}
