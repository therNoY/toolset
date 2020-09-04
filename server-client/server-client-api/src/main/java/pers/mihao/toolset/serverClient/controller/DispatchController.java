package pers.mihao.toolset.serverClient.controller;


import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.mihao.toolset.serverClient.ClientNode;
import pers.mihao.toolset.serverClient.dto.ReqTestConn;
import pers.mihao.toolset.serverClient.enums.ClientTypeEnum;
import pers.mihao.toolset.serverClient.service.ClientService;
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
        ClientService clientService = getHandelControllerByType(type);
        List<ClientNode> dateSource;
        dateSource = clientService.getSourceNodeList();
        return RespHelper.success(dateSource);
    }

    /**
     * 测试连接 是否ok
     * */
    @PostMapping("/api/connect/test")
    public RespResult testConnect(@Validated @RequestBody ReqTestConn reqTestConn, BindingResult result) {
        ClientService clientService = getHandelControllerByType(reqTestConn.getType());
        String connResult = clientService.testConnect(reqTestConn);
        return RespHelper.success(connResult);
    }

    /**
     * 根据传过来的类型获取对应的处理类
     * @param type
     * @return
     */
    private ClientService getHandelControllerByType(String type) {
        ClientService clientService;
        ClientTypeEnum clientTypeEnum = EnumUtil.valueOf(ClientTypeEnum.class, type);
        clientService = ApplicationContextHolder.getBean(clientTypeEnum.serverName);
        return clientService;
    }
}
