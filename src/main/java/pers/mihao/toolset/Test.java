package pers.mihao.toolset;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        System.out.println(Test.class.getMethod("sout", String.class, String.class).invoke(new Test(), "1", "2"));

    }

    public static String sout(String s,String s2){
        System.out.println("1");
        System.out.println(s + " " + s2);
        return s;
    }

    static class MyJob implements Runnable, Delayed{

        int delayTime;

        public MyJob(int delayTime) {
            this.delayTime = delayTime;
        }

        @Override
        public void run() {
            System.out.println("hahah" + delayTime);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return delayTime;
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (o.getDelay(TimeUnit.SECONDS) - getDelay(TimeUnit.SECONDS));
        }
    }

}
