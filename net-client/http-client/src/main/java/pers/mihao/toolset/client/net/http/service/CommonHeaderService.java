package pers.mihao.toolset.client.net.http.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.mihao.toolset.client.net.http.dto.RespCommonHeader;
import pers.mihao.toolset.client.net.http.entity.CommonHeader;

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
