package pers.mihao.toolset.client.redis;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import pers.mihao.toolset.serverClient.base.AbstractFactory;

/**
 * 创建连接的Factory
 */
public class RedisFactory extends AbstractFactory<RedisSource, RedisClient> {

    /**
     * 根据数据源创建连接
     * @param redisSource
     * @return
     * @throws Exception
     */
    @Override
    public PooledObject<RedisClient> makeObject(RedisSource redisSource) throws Exception {
        RedisClient redisClient = new RedisClient(redisSource);
        redisClient.connect();
        if (null != redisSource.getPassword()) {
            redisClient.auth(redisSource.getPassword());
        }
        return new DefaultPooledObject<>(redisClient);
    }

    @Override
    public void destroyObject(RedisSource key, PooledObject<RedisClient> rc) throws Exception {
        RedisClient redisClient = rc.getObject();
        if (redisClient.isConnected()) {
            try {
                try {
                    redisClient.quit();
                } catch (Exception e) {
                }
                redisClient.disconnect();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public boolean validateObject(RedisSource dataSource, PooledObject<RedisClient> p) {
        final RedisClient redisClient = p.getObject();
        try {
            String connectionHost = redisClient.getClient().getHost();
            int connectionPort = redisClient.getClient().getPort();

            return dataSource.getHost().equals(connectionHost)
                    && dataSource.getPort() == connectionPort && redisClient.isConnected()
                    && redisClient.ping().equals("PONG");
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public void activateObject(RedisSource source, PooledObject<RedisClient> p) throws Exception {
        final RedisClient redisClient = p.getObject();
        if (redisClient.getDB() != source.getDatabase()) {
            redisClient.select(source.getDatabase());
        }
    }

    @Override
    public void passivateObject(RedisSource key, PooledObject<RedisClient> p) throws Exception {
    }
}
