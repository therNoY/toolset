package pers.mihao.toolset.client.controller;


import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.mihao.toolset.client.base.ClientNode;
import pers.mihao.toolset.client.base.dto.ReqTestConn;
import pers.mihao.toolset.client.base.enums.ClientTypeEnum;
import pers.mihao.toolset.client.base.service.BaseSourceService;
import pers.mihao.toolset.client.zookeepr.service.ZookeeperService;
import pers.mihao.toolset.common.util.ApplicationContextHolder;
import pers.mihao.toolset.common.util.EnumUtil;
import pers.mihao.toolset.common.util.RespHelper;
import pers.mihao.toolset.common.vo.RespResult;

import java.util.List;

@RestController
public class DispatchController {

    /**
     * 获取用户连接类型的所有数据源
     * @param type
     * @return
     */
    @GetMapping("/api/connect/list")
    public RespResult getConnectList(@RequestParam String type) {
        BaseSourceService baseSourceService = getHandelControllerByType(type);
        List<ClientNode> dateSource = null;
        dateSource = baseSourceService.getSourceNodeList();
        return RespHelper.success(dateSource);
    }

    /**
     * 测试连接 是否ok
     * */
    @PostMapping("/api/connect/test")
    public RespResult testConnect(@Validated @RequestBody ReqTestConn reqTestConn, BindingResult result) {
        BaseSourceService baseSourceService = getHandelControllerByType(reqTestConn.getType());
        String connResult = baseSourceService.testConnect(reqTestConn);
        return RespHelper.success(connResult);
    }

    private BaseSourceService getHandelControllerByType(String type) {
        BaseSourceService baseSourceService = null;
        ClientTypeEnum clientTypeEnum = EnumUtil.valueOf(ClientTypeEnum.class, type);
        switch (clientTypeEnum) {
            case ZOOKEEPER:
                baseSourceService = ApplicationContextHolder.getBean(ZookeeperService.class);
                break;
        }
        return baseSourceService;
    }
}
