package pers.mihao.toolset.music.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自动执行获取音乐资源
 */
public class AuthGetMusic {


    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(3,
            10,
            30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(9),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());


    protected void getMusic(Runnable runnable){
        executor.submit(runnable);
    }
}
