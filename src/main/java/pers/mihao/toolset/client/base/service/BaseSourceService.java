package pers.mihao.toolset.client.base.service;

import pers.mihao.toolset.client.base.ClientNode;
import pers.mihao.toolset.client.base.dto.ReqTestConn;

import java.util.List;

public interface BaseSourceService {

    List<ClientNode> getSourceNodeList();

    String testConnect(ReqTestConn reqTestConn);
}
