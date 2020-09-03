package pers.mihao.toolset.client.redis;


import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

public class RedisPoolConfig extends GenericKeyedObjectPoolConfig {

    public RedisPoolConfig() {

        setTestWhileIdle(true);
        setMinEvictableIdleTimeMillis(60000);
        setTimeBetweenEvictionRunsMillis(30000);
        setNumTestsPerEvictionRun(-1);
    }
}
