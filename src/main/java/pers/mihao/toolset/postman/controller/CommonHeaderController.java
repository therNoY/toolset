package pers.mihao.toolset.postman.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.common.util.RespHelper;
import pers.mihao.toolset.common.vo.RespResult;
import pers.mihao.toolset.postman.dto.RespCommonHeader;
import pers.mihao.toolset.postman.service.CommonHeaderService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2020-01-24
 */
@RestController
public class CommonHeaderController {

    @Autowired
    CommonHeaderService commonHeaderService;

    /**
     * 获取
     * @return
     */
    @GetMapping("/postman/commonHeader")
    public RespResult getCommonHeaders(){
        RespCommonHeader respCommonHeader = commonHeaderService.getCommonHeader();
        return RespHelper.success(respCommonHeader);
    }
}
