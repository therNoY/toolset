package pers.mihao.toolset.serverClient.controller;


import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.serverClient.base.ClientNode;
import pers.mihao.toolset.serverClient.base.dto.ReqTestConn;
import pers.mihao.toolset.serverClient.base.enums.ClientTypeEnum;
import pers.mihao.toolset.serverClient.base.service.BaseSourceService;
import pers.mihao.toolset.util.ApplicationContextHolder;
import pers.mihao.toolset.util.EnumUtil;
import pers.mihao.toolset.util.RespHelper;
import pers.mihao.toolset.dto.RespResult;

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
                baseSourceService = ApplicationContextHolder.getBean("ZookeeperService");
                break;
        }
        return baseSourceService;
    }
}
