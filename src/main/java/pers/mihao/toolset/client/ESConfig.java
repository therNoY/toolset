package pers.mihao.toolset.client;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ESConfig {
    @Value("${elasticsearch.host}")
    private String host;

    private int port;

    public static final String MUSIC = "music";
    public static final String MUSIC163 = "music163";
    public static final String BOOK = "book";
    public static final String DOCUMENT = "document";


    /**
     * 防止netty的bug
     * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
     */
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }


    @Value("${elasticsearch.port}")
    private void setPort(String str){
        if(!StringUtils.isEmpty(str)){
            port = Integer.valueOf(str);
        }
    }
    @Value("${elasticsearch.cluster-name}")
    private String clusterName;

    @Bean
    public TransportClient client() throws UnknownHostException {

        //es集群连接
        TransportAddress node = new TransportAddress(
                InetAddress.getByName(host),
                port
        );

        //es集群配置（自定义配置） 连接自己安装的集群名称
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .build();

        PreBuiltTransportClient client = new PreBuiltTransportClient(settings);

        client.addTransportAddress(node);

        return client;
    }
}
