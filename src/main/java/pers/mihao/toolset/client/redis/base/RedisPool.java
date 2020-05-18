package pers.mihao.toolset.client.redis.base;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import pers.mihao.toolset.client.base.AbstractPool;
import pers.mihao.toolset.client.base.DataSource;


/***
 * redis 单机连接池
 */
public class RedisPool extends AbstractPool<RedisClient> {

    private static final int DEFAULT_PORT = 6379;
    private static final int DEFAULT_DATABASE = 0;


    public RedisPool(final RedisSource dataSource) {
        if (dataSource != null) {
            RedisFactory redisFactory = new RedisFactory();
            this.internalPool = new GenericKeyedObjectPool<>(redisFactory, new RedisPoolConfig());
        }
    }

    public RedisPool(RedisPoolConfig config, RedisFactory redisFactory) {
        this.internalPool = new GenericKeyedObjectPool<>(redisFactory, config);
    }

    @Override
    public RedisClient getResource(DataSource dataSource) {
        RedisClient redisClient = super.getResource(dataSource);
        redisClient.setDataSource(this);
        return redisClient;
    }
}
