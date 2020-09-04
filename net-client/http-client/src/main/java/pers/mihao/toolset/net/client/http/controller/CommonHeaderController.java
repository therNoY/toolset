package pers.mihao.toolset.net.client.http.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.net.client.http.dto.RespCommonHeader;
import pers.mihao.toolset.net.client.http.service.CommonHeaderService;
import pers.mihao.toolset.util.RespHelper;
import pers.mihao.toolset.dto.RespResult;

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
    @GetMapping("/httpReq/commonHeader")
    public RespResult getCommonHeaders(){
        RespCommonHeader respCommonHeader = commonHeaderService.getCommonHeader();
        return RespHelper.success(respCommonHeader);
    }
}
