package pers.mihao.toolset.client.zookeepr.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.mihao.toolset.client.zookeepr.dao.ZkSourceDao;
import pers.mihao.toolset.client.zookeepr.entity.ZkSource;
import pers.mihao.toolset.client.zookeepr.service.ZookeeperService;
import pers.mihao.toolset.serverClient.base.ClientNode;
import pers.mihao.toolset.serverClient.base.DataSourceConfig;
import pers.mihao.toolset.serverClient.base.dto.ReqTestConn;
import pers.mihao.toolset.serverClient.base.service.BaseSourceService;
import pers.mihao.toolset.util.AuthUtil;


@Service
public class ZookeeperServiceImpl extends ServiceImpl<ZkSourceDao, ZkSource> implements ZookeeperService,
    BaseSourceService {

    @Autowired
    ZkSourceDao zkSourceDao;

    @Override
    public List<ClientNode> getSourceNodeList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        Integer userId = AuthUtil.getAuthId();
        queryWrapper.eq("user_id", userId);
        List<ZkSource> zkList = zkSourceDao.selectList(queryWrapper);
        List<ClientNode> clientNodeList = zkList.stream().map(this::sourceToNode).collect(Collectors.toList());
        return clientNodeList;
    }

    /**
     * 测试ZK 连接
     * @param reqTestConn
     * @return
     */
    @Override
    public String testConnect(ReqTestConn reqTestConn) {
        return null;
    }

    public ClientNode sourceToNode(DataSourceConfig source){
        ZkSource zkSource = (ZkSource) source;
        ClientNode clientNode = new ClientNode();
        clientNode.setIcon(ZkSource.connectIcon);
        clientNode.setName(zkSource.getName());
        clientNode.setId(zkSource.getId());
        clientNode.setRoot(true);
        return clientNode;
    }

}