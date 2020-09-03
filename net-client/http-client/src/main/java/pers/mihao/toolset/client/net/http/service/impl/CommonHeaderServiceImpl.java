package pers.mihao.toolset.client.net.http.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.mihao.toolset.constant.RedisKey;
import pers.mihao.toolset.client.net.http.dto.RespCommonHeader;
import pers.mihao.toolset.client.net.http.entity.CommonHeader;
import pers.mihao.toolset.client.net.http.dao.CommonHeaderDao;
import pers.mihao.toolset.client.net.http.service.CommonHeaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-01-24
 */
@Service
public class CommonHeaderServiceImpl extends ServiceImpl<CommonHeaderDao, CommonHeader> implements CommonHeaderService {

    @Autowired
    CommonHeaderDao commonHeaderDao;

    /**
     * 查询通用的http请求头
     * @return
     */
    @Override
    @Cacheable(RedisKey.HTTP_COMMON_HEADERS)
    public RespCommonHeader getCommonHeader() {
        RespCommonHeader respCommonHeader = new RespCommonHeader();
        List<CommonHeader> commonHeaderList = commonHeaderDao.findAll();
        String[] strings = new String[commonHeaderList.size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = commonHeaderList.get(i).getKey();
        }
        respCommonHeader.setHeaderKeyList(RespCommonHeader.makeHeaderKeys(strings));
        respCommonHeader.setCommonHeaderList(commonHeaderList.stream().filter(commonHeader -> commonHeader.getDefaultUse() == 1).collect(Collectors.toList()));
        return respCommonHeader;
    }
}
