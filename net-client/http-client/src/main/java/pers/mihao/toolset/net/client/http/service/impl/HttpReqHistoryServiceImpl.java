package pers.mihao.toolset.net.client.http.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pers.mihao.toolset.auth.AuthUtil;
import pers.mihao.toolset.constant.RedisKey;
import pers.mihao.toolset.net.client.http.dto.HttpReqDetail;
import pers.mihao.toolset.net.client.http.entity.HttpReqHistory;
import pers.mihao.toolset.auth.AuthUtil;
import pers.mihao.toolset.net.client.http.dao.HttpReqHistoryDao;
import pers.mihao.toolset.net.client.http.dto.ReqAddHistory;
import pers.mihao.toolset.net.client.http.dto.RespHistoryList;
import pers.mihao.toolset.net.client.http.service.HttpReqHistoryService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2020-01-05
 */
@Service
public class HttpReqHistoryServiceImpl extends ServiceImpl<HttpReqHistoryDao, HttpReqHistory> implements HttpReqHistoryService {

    @Autowired
    HttpReqHistoryDao httpReqHistoryDao;

    /**
     * 添加用户历史纪录
     *
     * @param reqAddHistory
     * @param userId
     */
    @Override
    public void addUserHistory(ReqAddHistory reqAddHistory, Integer userId) {
        HttpReqHistory history = new HttpReqHistory();
        history.setUserId(AuthUtil.getAuthId());
        history.setCreateDate(LocalDateTime.now());
        history.setType(reqAddHistory.getType());
        history.setUrl(reqAddHistory.getUrl());
        save(history);
    }


    /**
     * 根据用户获取用户的http请求 历史
     *
     * @param filter
     * @param userId
     * @return
     */
    @Override
    public List<RespHistoryList> getUserHistory(String filter, Integer userId) {
        // 1.获取用户最近100条
        if (filter != null && filter.length() > 0) {
            return getFilterHistory(filter,userId);
        } else {
            return getAllHistory(userId);
        }
    }


    public List<RespHistoryList> getFilterHistory(String filter, Integer userId){
        List<HttpReqHistory> historyAndFilter  = httpReqHistoryDao.getUserHistoryAndFilter(filter, userId);
        return orderHistory(historyAndFilter);
    }

    @Cacheable(RedisKey.USER_HTTP_REQ_HISTORY)
    public List<RespHistoryList> getAllHistory(Integer userId){
        List<HttpReqHistory> itemByStatus = httpReqHistoryDao.getUserItemByStatus(userId);
        return orderHistory(itemByStatus);
    }

    private List<RespHistoryList> orderHistory(List<HttpReqHistory> httpReqHistories){
        Map<String, List<HttpReqHistory>> historyMap = new TreeMap<>((s1, s2) -> s1.compareTo(s2) * -1);

        // 2. 根据时间进行整理
        httpReqHistories.forEach(history -> {
            LocalDateTime time = history.getCreateDate();
            String date = new StringBuilder().append(time.getYear()).append("-")
                    .append(getMonthValue(time)).append("-")
                    .append(getDay(time)).toString();
            List<HttpReqHistory> historyList = historyMap.get(date);
            if (historyList == null) {
                historyList = new ArrayList<>();
                historyMap.put(date, historyList);
            }

            HttpReqDetail detail = JSON.parseObject(history.getDetail(), HttpReqDetail.class);
            history.setHttpReqDetail(detail);
            history.setLabel(history.getUrl());

            historyList.add(history);
        });

        // 设置id 从-数开始
        List<RespHistoryList> lists = new ArrayList<>();
        AtomicInteger id = new AtomicInteger();
        historyMap.forEach((key, list) -> {
            RespHistoryList respHistoryList = new RespHistoryList();
            respHistoryList.setName(key);
            respHistoryList.setChildren(list.stream().
                    sorted((h1, h2) -> h1.getCreateDate().compareTo(h2.getCreateDate()) * -1).
                    collect(Collectors.toList()));
            respHistoryList.setId(id.getAndDecrement());
            lists.add(respHistoryList);
        });

        return lists;
    }

    @Override
    public boolean delHistory(Integer userId, Integer id) {
        HttpReqHistory history = httpReqHistoryDao.selectById(id);
        if (history.getUserId().equals(userId)) {
            httpReqHistoryDao.deleteById(id);
            return true;
        }

        return false;
    }


    private String getMonthValue(LocalDateTime time) {
        int m = time.getMonthValue();
        if (m > 10) {
            return String.valueOf(m);
        } else {
            return "0" + m;
        }
    }

    private String getDay(LocalDateTime time) {
        int m = time.getDayOfMonth();
        if (m > 10) {
            return String.valueOf(m);
        } else {
            return "0" + m;
        }
    }
}
