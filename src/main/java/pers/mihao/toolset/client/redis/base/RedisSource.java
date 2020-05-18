package pers.mihao.toolset.client.redis.base;

import pers.mihao.toolset.client.base.DataSource;

public class RedisSource extends DataSource {


    public static int DEFAULT_PORT = 6379;
    public static int DEFAULT_DATA_BASE = 0;

    private int database;

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }
}
