package pers.mihao.toolset.postman.service;

import pers.mihao.toolset.postman.dto.RespCommonHeader;
import pers.mihao.toolset.postman.entity.CommonHeader;
import com.baomidou.mybatisplus.extension.service.IService;

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
