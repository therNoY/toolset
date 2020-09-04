package pers.mihao.toolset.net.client.http.service.impl;

import static pers.mihao.toolset.net.client.http.consts.enums.PostmanConst.defaultItemVersionId;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pers.mihao.toolset.common.annotation.KnowledgePoint;
import pers.mihao.toolset.net.client.http.consts.enums.ItemStatus;
import pers.mihao.toolset.net.client.http.consts.enums.PostmanConst;
import pers.mihao.toolset.net.client.http.dao.HttpReqItemDao;
import pers.mihao.toolset.net.client.http.dto.*;
import pers.mihao.toolset.net.client.http.dto.HttpReqDetail;
import pers.mihao.toolset.net.client.http.dto.HttpReqDetail.RequestEntry;
import pers.mihao.toolset.net.client.http.entity.CommonHeader;
import pers.mihao.toolset.net.client.http.entity.HttpReqHistory;
import pers.mihao.toolset.net.client.http.entity.HttpReqItem;
import pers.mihao.toolset.net.client.http.service.CommonHeaderService;
import pers.mihao.toolset.net.client.http.service.HttpReqService;
import pers.mihao.toolset.net.client.http.service.HttpReqHistoryService;
import pers.mihao.toolset.net.client.http.service.HttpReqItemService;
import pers.mihao.toolset.constant.RedisKey;
import pers.mihao.toolset.enums.HttpMethod;
import pers.mihao.toolset.auth.AuthUtil;
import pers.mihao.toolset.util.EnumUtil;
import pers.mihao.toolset.dto.MyException;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2020-01-05
 */
@Service
public class PostmanItemServiceImpl extends ServiceImpl<HttpReqItemDao, HttpReqItem> implements HttpReqItemService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpReqItemDao httpReqItemDao;
    @Autowired
    CommonHeaderService commonHeaderService;
    @Autowired
    HttpReqHistoryService httpReqHistoryService;
    @Autowired
    HttpReqService collectionService;

    /**
     * 获取用户的编辑记录
     *
     * @param userId
     * @return
     */
    @Override
    @Cacheable(RedisKey.USER_HTTP_REQ_EDIT)
    public List<HttpReqItem> getUserEdit(Integer userId) {
        List<HttpReqItem> editList = httpReqItemDao.getUserItemByStatus(userId, ItemStatus.EDIT, ItemStatus.SAVE_AND_EDIT);
        editList.forEach(httpReqItem -> warpPostmanItem(httpReqItem));
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
        httpReqItemDao.updateStatus(id, status);
    }

    /**
     * 保存item
     *
     * @param saveItem
     * @param userId
     */
    @Override
    public Integer saveItem(ReqSaveItem saveItem, int userId) {
        HttpReqItem httpReqItem = initEmptyItem(userId);
        httpReqItem.setUrl(saveItem.getLabel());
        httpReqItem.setDetail(saveItem.getHttpReqDetail().toJSONString());
        httpReqItem.setType(saveItem.getType());
        httpReqItem.setHistoryId(saveItem.getHistoryId());
        httpReqItemDao.insert(httpReqItem);
        return httpReqItem.getId();
    }

    /**
     * 更新item 主要是更新参数
     *
     * @param updateItem
     * @return
     */
    @KnowledgePoint("将Integer 与 int比较的时候如果Integer 对象是空会空指针")
    @Override
    public HttpReqItem updateItem(ReqUpdateItem updateItem) {
        updateItem.setDetail(JSON.toJSONString(updateItem.getHttpReqDetail()));
        if (!PostmanConst.emptyUrl.equals(updateItem.getLabel()) && updateItem.getLabel() != null) {
            updateItem.setUrl(getUrl(updateItem));
        } else {
            updateItem.setUrl(updateItem.getLabel());
        }


        // 如果现在是最开始的版本就需要备份
        if (updateItem.getLastVersionId() == null || updateItem.getLastVersionId() == defaultItemVersionId) {
            HttpReqItem item = getById(updateItem.getId());
            item.setId(null);
            item.setStatus(ItemStatus.EDIT_HISTORY);
            save(item);
            updateItem.setLastVersionId(item.getId());
        }

        httpReqItemDao.updateById(updateItem);

        HttpReqItem item;
        if ((item = httpReqItemDao.selectById(updateItem.getId())) != null) {
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
    public HttpReqItem updateItemUrl(ReqUpdateItem updateItem) {
        String[] splits = updateItem.getLabel().split("\\?");
        List<HttpReqDetail.RequestEntry> nowList = new ArrayList<>();
        updateItem.getHttpReqDetail().getParams().forEach(entry -> {
            if (!entry.getS()) {
                nowList.add(entry);
            }
        });
        // 目录中含有参数
        if (splits.length > 1) {
            List<String> pars = Arrays.asList(splits[1].split("&"));
            pars.forEach(s -> {
                HttpReqDetail.RequestEntry entry = new HttpReqDetail.RequestEntry();
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
        updateItem.getHttpReqDetail().setParams(nowList);
        updateItem.setDetail(JSON.toJSONString(updateItem.getHttpReqDetail()));

        // 如果现在是最开始的版本就需要备份
        if (updateItem.getLastVersionId() != null && updateItem.getLastVersionId() == defaultItemVersionId) {
            HttpReqItem item = getById(updateItem.getId());
            item.setId(null);
            item.setStatus(ItemStatus.EDIT_HISTORY);
            save(item);
            updateItem.setLastVersionId(item.getId());
        }
        httpReqItemDao.updateById(updateItem);
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
    public HttpReqItem addNewItem(int userId, ReqCollectIdDto collectId) {
        HttpReqItem httpReqItem = initEmptyItem(userId);
        if (collectId != null && collectId.getCollectId() != null) {
            httpReqItem.setStatus(ItemStatus.SAVE_AND_EDIT);
            httpReqItem.setCollectionsId(collectId.getCollectId());
            httpReqItem.setName(collectId.getName());
            collectionService.removeUserCollectCatch(userId);
        }
        httpReqItemDao.insert(httpReqItem);
        return httpReqItem;
    }

    private HttpReqItem initEmptyItem(int userId) {
        HttpReqItem httpReqItem = new HttpReqItem();
        httpReqItem.setUserId(userId);
        httpReqItem.setStatus(ItemStatus.EDIT);
        httpReqItem.setUrl(PostmanConst.emptyUrl);
        httpReqItem.setName(PostmanConst.emptyName);
        httpReqItem.setType(HttpMethod.GET.type());
        httpReqItem.setCreateDate(LocalDateTime.now());
        HttpReqDetail httpReqDetail = new HttpReqDetail();
        httpReqDetail.setParams(new ArrayList<>());
        httpReqDetail.setAuth(new MapEntry<>());
        httpReqDetail.setBody("");
        httpReqDetail.setHeaders(new ArrayList<>());
        httpReqItem.setDetail(JSONObject.toJSONString(httpReqDetail));
        httpReqItem.setHttpReqDetail(httpReqDetail);
        return httpReqItem;
    }


    /**
     * 发送http请求
     *
     * @param item
     * @param addDefaultHeads
     * @return
     */
    @Override
    public HttpReqItem sendHttp(HttpReqItem item, boolean addDefaultHeads) {
        if (sendHttpForRes(item, addDefaultHeads)) return null;
        // 增加历史纪录
        HttpReqHistory history = new HttpReqHistory();
        history.setUrl(item.getUrl());
        history.setUserId(AuthUtil.getAuthId());
        history.setType(item.getType());
        history.setCreateDate(LocalDateTime.now());
        history.setDetail(JSON.toJSONString(item.getHttpReqDetail()));
        httpReqHistoryService.save(history);

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
    public boolean sendHttpForRes(HttpReqItem item, boolean addDefaultHeads) {
        log.info("准备发送http请求{}", item);

        HttpConnection httpConnection = (HttpConnection) Jsoup.connect(item.getUrl());
        List<CommonHeader> commonHeaders = commonHeaderService.getCommonHeader().getCommonHeaderList();

        // 设置请求头
        List<RequestEntry> entries = item.getHttpReqDetail().getHeaders();
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
        MapEntry<String> authKey = item.getHttpReqDetail().getAuth();
        if (authKey.getV() != null) {
            if (authKey.getK().equals("Bearer Token")) {
                httpConnection.header(HttpConnection.AuthKey, "Bearer " + authKey.getV());
            }
        }



        try {
            String body = item.getHttpReqDetail().getBody();
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
    @Cacheable(RedisKey.USER_HTTP_REQ_SAVE_ITEM)
    public List<HttpReqItem> getUserSaveItem(Integer userId) {
        List<HttpReqItem> editList = httpReqItemDao.getUserItemByStatus(userId, ItemStatus.SAVE, ItemStatus.SAVE_AND_EDIT);
        return editList;
    }


    @Override
    public void deleteByCollectId(Integer authId, Integer id) {
        httpReqItemDao.deleteByCollectId(authId, id);
    }

    @Override
    @CacheEvict(RedisKey.USER_HTTP_REQ_SAVE_ITEM)
    public void deleteUserSaveItemCatch(Integer userId) {
    }


    @Override
    @CacheEvict(RedisKey.USER_HTTP_REQ_EDIT)
    public void deleteUserEditCatch(Integer userId) {
    }

    /**
     *
     * @param id
     */
    @Override
    public void notSaveEditItem(String id) {
        HttpReqItem item = getById(id);

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
    public HttpReqItem saveItemChange(Integer id) {
        HttpReqItem httpReqItem = getById(id);
        int versionId;
        if ((versionId = httpReqItem.getLastVersionId()) != defaultItemVersionId) {
            httpReqItem.setStatus(ItemStatus.SAVE);
            httpReqItem.setLastVersionId(-1);
            updateById(httpReqItem);
            removeById(versionId);
            return httpReqItem;
        }
        return null;
    }

    private void rollbackItem(HttpReqItem item) {
        removeById(item.getLastVersionId());
        item.setStatus(ItemStatus.SAVE);
        item.setLastVersionId(defaultItemVersionId);
        updateById(item);
    }

    /**
     * @param httpReqItem
     * @return
     */
    public String getUrl(HttpReqItem httpReqItem) {
        StringBuilder url = new StringBuilder(httpReqItem.getLabel().split("\\?")[0]);
        if (httpReqItem.getHttpReqDetail() != null && httpReqItem.getHttpReqDetail().getParams() != null) {
            url.append("?");
            List<HttpReqDetail.RequestEntry> entries = httpReqItem.getHttpReqDetail().getParams();
            for (int i = 0; i < entries.size(); i++) {
                HttpReqDetail.RequestEntry entry = entries.get(i);
                if (entry.getS() && entry.getK().length() > 0) {
                    url.append(entry.getK()).append("=").append(entry.getV()).append("&");
                }
            }
            return url.toString().substring(0, url.length() - 1);
        }
        return url.toString();
    }

    @Override
    public HttpReqItem saveItemChange(ReqSaveItemDto saveItemDto) {
        HttpReqItem httpReqItem = getById(saveItemDto.getId());
        int versionId;
        if ((versionId = httpReqItem.getLastVersionId()) != defaultItemVersionId) {
            httpReqItem.setStatus(ItemStatus.SAVE_AND_EDIT);
            httpReqItem.setLastVersionId(-1);
            updateById(httpReqItem);
            removeById(versionId);
            return warpPostmanItem(httpReqItem);
        }
        return null;
    }

    public HttpReqItem warpPostmanItem(HttpReqItem item) {
        item.setLabel(item.getUrl());

        if (item.getDetail() != null){
            item.setHttpReqDetail(JSON.parseObject(item.getDetail(), HttpReqDetail.class));
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
