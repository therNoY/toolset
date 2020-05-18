package pers.mihao.toolset.redisTest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import pers.mihao.toolset.client.redis.base.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * 该测试文件用来对比 Jedis 对common pool 的使用 和抽象出来的使用对比
 */
public class JedisTest {

    Logger log = LoggerFactory.getLogger(JedisTest.class);


    private Jedis getConn() {
        Jedis jedis = new Jedis("10.0.11.155", 7001);
        jedis.auth("testmaster123");
        return jedis;
    }


    @Test
    public void testPip() {


//        long start = System.currentTimeMillis();
//        Pipeline pipeline = jedis.pipelined();
//        byte[][] bytes = new byte[outcallTasks.size()][];
//        for (int i = 0; i < outcallTasks.size(); i++) {
//            OutcallTask task = outcallTasks.get(i);
//            task.setStatus(TaskStatus.RUNNABLE);
//            pipeline.setex((RedisKey.TASK_ + outcallTasks.get(i).getTaskId()).getBytes(), 1000, JsonUtil.toJson(outcallTasks.get(i)).getBytes());
//            bytes[i] = JsonUtil.toJson(task).getBytes();
//        }
//        pipeline.lpush("ivr-20191212-105714-0001-400_organ1".getBytes(), bytes);
//        pipeline.sync();
//        System.out.println(System.currentTimeMillis() - start);

//        long loaderTask = System.currentTimeMillis();
//        outcallTasks.stream().forEach(task -> {
//            task.setStatus(TaskStatus.RUNNABLE);
//            jedis.setex(RedisKey.TASK_ + task.getTaskId(), 1000, JsonUtil.toJson(task));
//            jedis.lpush("ivr-20191212-105714-0001-400_organ1", task.getTaskId());
//        });
//        System.out.println(System.currentTimeMillis() - loaderTask);


    }


    private List initTestData() {


        return null;
    }


    @Test
    public void name() {
        JedisPoolConfig config = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(config, "10.0.11.64");
        Jedis jedis = jedisPool.getResource();
        jedis.set("aa", "bb");
        System.out.println(jedis.get("aa"));
        jedis.close();
        jedisPool.close();
    }


    @Test
    public void testRedisReadAndWrite() throws InterruptedException {

        int threadNum = 5;

        CountDownLatch countDownLatch = new CountDownLatch(threadNum);

        long mainStart = System.currentTimeMillis();

        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {

                long start = System.currentTimeMillis();

                Jedis jedis = getConn();


                for (int j = 0; j < 1000; j++) {
//                    jedis.hset(Thread.currentThread().getName(), "key" + j, UUID.randomUUID().toString());

                    if (j > 0) {
                        jedis.hget(Thread.currentThread().getName(), "key" + new Random().nextInt(1000));
                        jedis.hget(Thread.currentThread().getName(), "key" + new Random().nextInt(1000));
                        jedis.hget(Thread.currentThread().getName(), "key" + new Random().nextInt(1000));
                        jedis.hget(Thread.currentThread().getName(), "key" + new Random().nextInt(1000));
                    }

                    if (j != 0 && j % 200 == 0) {
                        log.info("当前插入条数{}", j);
                    }
                }

                countDownLatch.countDown();

                jedis.close();
                log.info("一个线程写入一万条数据使用时间{}", System.currentTimeMillis() - start);
            }, "thread-" + i).start();
        }

        countDownLatch.await();

        log.info("全部线程写完使用时间{}", System.currentTimeMillis() - mainStart);
    }

    @Test
    public void name2() throws InterruptedException {
        RedisPoolConfig config = new RedisPoolConfig();
        RedisFactory redisFactory = new RedisFactory();
        RedisPool redisPool = new RedisPool(config, redisFactory);

        RedisSource redisSource = new RedisSource();
        redisSource.setHost("10.0.11.154");
        redisSource.setPassword("12345");
        redisSource.setPort(RedisSource.DEFAULT_PORT);
        redisSource.setDatabase(2);
        RedisClient redisClient = redisPool.getResource(redisSource);
        System.out.println("获取的redis" + redisClient);
        redisClient.set("aa", "bb");

        System.out.println(redisClient.get("aa"));

        new Thread(() -> {
            RedisClient redisClient2 = redisPool.getResource(redisSource);
            System.out.println("获取的redis" + redisClient2);
            redisClient2.close();
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            RedisClient redisClient2 = redisPool.getResource(redisSource);
            System.out.println("获取的redis" + redisClient2);
            redisClient2.close();
        }).start();

        redisClient.close();


        Thread.sleep(Integer.MAX_VALUE);
        redisPool.close();
    }
}
