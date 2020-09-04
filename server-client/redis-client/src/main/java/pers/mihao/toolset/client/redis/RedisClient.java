package pers.mihao.toolset.client.redis;

import pers.mihao.toolset.serverClient.AbstractPool;
import pers.mihao.toolset.serverClient.Client;
import pers.mihao.toolset.serverClient.DataSourceConfig;
import redis.clients.jedis.Jedis;

/**
 *  使用类适配器
 */
public class RedisClient extends Jedis implements Client {

    protected AbstractPool<RedisClient> redisPool = null;

    public RedisClient(DataSourceConfig dataSource) {
        super(dataSource.getHost(), dataSource.getPort());
    }

    public void close(RedisSource source) {
        if (redisPool != null) {
            this.redisPool.returnResource(source, this);
        } else {
            client.close();
        }
    }

    public void setDataSource(AbstractPool<RedisClient> jedisPool) {
        this.redisPool = jedisPool;
    }
}
