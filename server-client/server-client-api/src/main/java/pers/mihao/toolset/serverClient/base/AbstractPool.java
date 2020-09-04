package pers.mihao.toolset.serverClient.base;

import java.io.Closeable;
import java.util.NoSuchElementException;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.mihao.toolset.dto.MyException;

/**
 * 抽象构造对应资源的连接池
 * @param <T> 持久的客户端
 */
public abstract class AbstractPool<T> implements Closeable {


    private Logger log = LoggerFactory.getLogger(AbstractPool.class);

    /**
     *  使用
     */
    protected GenericKeyedObjectPool<DataSourceConfig, T> internalPool;

    public AbstractPool() {
    }

    public AbstractPool(final GenericKeyedObjectPoolConfig poolConfig, KeyedPooledObjectFactory<DataSourceConfig, T> factory) {
        initPool(poolConfig, factory);
    }

    @Override
    public void close() {
        destroy();
    }

    public boolean isClosed() {
        return this.internalPool.isClosed();
    }

    public void initPool(final GenericKeyedObjectPoolConfig poolConfig, KeyedPooledObjectFactory<DataSourceConfig, T> factory) {
        if (this.internalPool != null) {
            try {
                closeInternalPool();
            } catch (Exception e) {
            }
        }
        this.internalPool = new GenericKeyedObjectPool<DataSourceConfig, T>(factory, poolConfig);
    }

    public T getResource(DataSourceConfig dataSourceConfig) {
        try {
            return internalPool.borrowObject(dataSourceConfig);
        } catch (NoSuchElementException nse) {
            throw new MyException("Could not get a resource from the pool", nse);
        } catch (Exception e) {
            throw new MyException("Could not get a resource from the pool", e);
        }
    }

    public void returnResourceObject(DataSourceConfig dataSourceConfig, final T resource) {
        if (resource == null) {
            return;
        }
        try {
            internalPool.returnObject(dataSourceConfig, resource);
        } catch (Exception e) {
            throw new MyException("Could not return the resource to the pool", e);
        }
    }


    public void returnBrokenResource(DataSourceConfig dataSourceConfig, final T resource) {
        if (resource != null) {
            returnBrokenResourceObject(dataSourceConfig, resource);
        }
    }

    public void returnResource(DataSourceConfig dataSourceConfig, final T resource) {
        if (resource != null) {
            returnResourceObject(dataSourceConfig, resource);
        }
    }

    public void destroy() {
        closeInternalPool();
    }

    protected void returnBrokenResourceObject(DataSourceConfig dataSourceConfig, final T resource) {
        try {
            internalPool.invalidateObject(dataSourceConfig, resource);
        } catch (Exception e) {
            throw new MyException("Could not return the resource to the pool", e);
        }
    }

    protected void closeInternalPool() {
        try {
            internalPool.close();
        } catch (Exception e) {
            throw new MyException("Could not destroy the pool", e);
        }
    }

    public int getNumActive() {
        if (poolInactive()) {
            return -1;
        }
        return this.internalPool.getNumActive();
    }

    public int getNumIdle() {
        if (poolInactive()) {
            return -1;
        }

        return this.internalPool.getNumIdle();
    }

    public int getNumWaiters() {
        if (poolInactive()) {
            return -1;
        }

        return this.internalPool.getNumWaiters();
    }

    public long getMeanBorrowWaitTimeMillis() {
        if (poolInactive()) {
            return -1;
        }

        return this.internalPool.getMeanBorrowWaitTimeMillis();
    }

    public long getMaxBorrowWaitTimeMillis() {
        if (poolInactive()) {
            return -1;
        }
        return this.internalPool.getMaxBorrowWaitTimeMillis();
    }

    private boolean poolInactive() {
        return this.internalPool == null || this.internalPool.isClosed();
    }

    public void addObjects(int count, DataSourceConfig dataSourceConfig) {
        try {
            for (int i = 0; i < count; i++) {
                this.internalPool.addObject(dataSourceConfig);
            }
        } catch (Exception e) {
            throw new MyException("Error trying to add idle objects", e);
        }
    }
}
