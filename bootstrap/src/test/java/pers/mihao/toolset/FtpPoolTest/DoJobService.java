package pers.mihao.toolset.FtpPoolTest;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DoJobService {

    static String path = "";

    static String savePath = "C:\\Users\\Administrator\\Desktop\\test";

    static Executor executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        FtpPool ftpPool = new FtpPool();
        ftpPool.downloadFiles("4g7vzl.jpg", savePath);

    }

}
