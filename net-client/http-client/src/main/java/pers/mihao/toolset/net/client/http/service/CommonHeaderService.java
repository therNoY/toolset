package pers.mihao.toolset.net.client.http.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.mihao.toolset.net.client.http.dto.RespCommonHeader;
import pers.mihao.toolset.net.client.http.entity.CommonHeader;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-01-24
 */
public interface CommonHeaderService extends IService<CommonHeader> {

    RespCommonHeader getCommonHeader();
}
