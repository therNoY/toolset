package pers.mihao.toolset.FtpPoolTest;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.mihao.toolset.common.util.FTPUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FtpPool {

    /**
     * 日志对象
     **/
    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    /**
     * 该目录不存在
     */
    public static final String DIR_NOT_EXIST = "该目录不存在";

    /**
     * "该目录下没有文件
     */
    public static final String DIR_CONTAINS_NO_FILE = "该目录下没有文件";

    /**
     * FTP地址
     **/
    private String ftpAddress = "192.168.56.101";
    /**
     * FTP端口
     **/
    private int ftpPort = 21;
    /**
     * FTP用户名
     **/
    private String ftpUsername = "mihao";
    /**
     * FTP密码
     **/
    private String ftpPassword = "mihao2018";
    /**
     * FTP基础目录
     **/
    private String basePath = "apache-tomcat-8.5.43/webapps/img/";

    /**
     * 本地字符编码
     **/
    private static String localCharset = "GBK";

    /**
     * FTP协议里面，规定文件名编码为iso-8859-1
     **/
    private static String serverCharset = "ISO-8859-1";

    /**
     * UTF-8字符编码
     **/
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * OPTS UTF8
     * 字符串常量
     **/
    private static final String OPTS_UTF8 = "OPTS UTF8";

    /**
     * 设置缓冲区大小4M
     **/
    private static final int BUFFER_SIZE = 1024 * 1024 * 4;

    final int poolSize = 10;

    AtomicInteger instanceSize = new AtomicInteger(0);


    BlockingQueue<FTPClient> clientQueue = new LinkedBlockingQueue<>(poolSize);

    static Executor executor = Executors.newFixedThreadPool(10);


    /**
     * 下载该目录下所有文件到本地
     *
     * @param ftpPath  FTP服务器上的相对路径，例如：test/123
     * @param savePath 保存文件到本地的路径，例如：D:/test
     * @return 成功返回true，否则返回false
     */
    public boolean downloadFiles(String ftpPath, String savePath) {

        String extendFilePath;
        String fileName;

        int index = ftpPath.lastIndexOf("/");

        if (index < 0) {
            fileName = ftpPath;
            extendFilePath = "";
        }else {
            fileName = ftpPath.substring(index + 1);
            extendFilePath = ftpPath.substring(0, index + 1);
        }


        FTPClient ftpClient = getFtpClient();
        if (ftpClient != null) {
            try {
                String path = basePath + extendFilePath;
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    logger.error(basePath + ftpPath + DIR_NOT_EXIST);
                    return false;
                }
                // 设置被动模式，开通一个端口来传输数据
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
//                ftpClient.enterRemotePassiveMode();
//                ftpClient.enterLocalActiveMode();
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    logger.error(basePath + ftpPath + DIR_CONTAINS_NO_FILE);
                    return false;
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    File file = new File(savePath + '/' + ftpName);
                    OutputStream os = null;
                    try {
                        os = new FileOutputStream(file);
                        ftpClient.retrieveFile(ff, os);
                        logger.info("{}客户端下载...", ftpClient);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    os.flush();
                    os.close();

                }
            } catch (IOException e) {
                logger.error("下载文件失败", e);
            } finally {
                closeConnect(ftpClient);
            }
        }
        return Boolean.TRUE;
    }


    private void releaseClient(FTPClient client){
        try {
            clientQueue.put(client);
            logger.info("归还client:{}", client);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private FTPClient getFtpClient() {

        FTPClient client = getFtpClientFromQueue();
        if (client!= null && testConn(client)) {
            return client;
        }else {
            logger.warn("获取无效的client:{}", client);
            instanceSize.decrementAndGet();
            return getFtpClientFromQueue();
        }

    }

    private FTPClient getFtpClientFromQueue() {
        logger.info("准备获取client 已经存在的{}", instanceSize.get());
        if (instanceSize.get() < poolSize) {
            synchronized (FtpPool.class) {
                if (instanceSize.get() < poolSize) {
                    FTPClient client = getNewInstance();
                    if (client != null) {
                        logger.warn("创建client:{}", client);
                        instanceSize.incrementAndGet();
                        return client;
                    }
                }
            }
        }

        try {
            FTPClient client =  clientQueue.poll(5, TimeUnit.SECONDS);
            logger.warn("从池子中获取:{}", client);
            return client;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean testConn(FTPClient client){
        int reply = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            closeConnect(client);
            logger.error("FTP服务器连接失败");
            return false;
        }
        return true;
    }


    private FTPClient getNewInstance(){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpAddress, ftpPort);
            ftpClient.login(ftpUsername, ftpPassword);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //限制缓冲区大小
            ftpClient.setBufferSize(BUFFER_SIZE);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                closeConnect(ftpClient);
                logger.error("FTP服务器连接失败");
                return null;
            }

            return ftpClient;
        } catch (Exception e) {
            logger.error("FTP登录失败", e);
        }

        return null;
    }



    /**
     * FTP服务器路径编码转换
     *
     * @param ftpPath FTP服务器路径
     * @return String
     */
    private static String changeEncoding(String ftpPath, FTPClient ftpClient) {
        String directory = null;
        try {
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
                localCharset = CHARSET_UTF8;
            }
            directory = new String(ftpPath.getBytes(localCharset), serverCharset);
        } catch (Exception e) {
            logger.error("路径编码转换失败", e);
        }
        return directory;
    }

    /**
     * 关闭FTP连接
     */
    private void closeConnect(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error("关闭FTP连接失败", e);
            }
        }
    }

}
