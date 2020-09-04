package pers.mihao.toolset.serverClient.service;

import java.util.List;
import pers.mihao.toolset.serverClient.ClientNode;
import pers.mihao.toolset.serverClient.dto.ReqTestConn;

/**
 * 基础客户端服务类
 */
public interface ClientService {

    /**
     * 获取用户保存的所有连接
     * @return
     */
    List<ClientNode> getSourceNodeList();

    /**
     * 测试连接
     * @param reqTestConn
     * @return
     */
    String testConnect(ReqTestConn reqTestConn);
}
