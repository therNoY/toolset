package pers.mihao.toolset.serverClient.base;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
/**
 * 该类相当于  JedisFactory 的抽象父类
 *
 * @param <K, T>
 */
public abstract class AbstractFactory<K, T> implements KeyedPooledObjectFactory<K, T> {

    @Override
    public PooledObject<T> makeObject(K k) throws Exception {
        return null;
    }

    @Override
    public void destroyObject(K k, PooledObject<T> p) throws Exception {

    }

    @Override
    public boolean validateObject(K k, PooledObject<T> p) {
        return false;
    }

    @Override
    public void activateObject(K k, PooledObject<T> p) throws Exception {

    }

    @Override
    public void passivateObject(K k, PooledObject<T> p) throws Exception {

    }
}
