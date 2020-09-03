package pers.mihao.toolset.serverClient.base.service;

import java.util.List;
import pers.mihao.toolset.serverClient.base.ClientNode;
import pers.mihao.toolset.serverClient.base.dto.ReqTestConn;

public interface BaseSourceService {

    List<ClientNode> getSourceNodeList();

    String testConnect(ReqTestConn reqTestConn);
}
