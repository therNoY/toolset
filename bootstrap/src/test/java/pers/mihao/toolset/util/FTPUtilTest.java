package pers.mihao.toolset.util;

import org.junit.Test;
import pers.mihao.toolset.util.FTPUtil;

public class FTPUtilTest {

    @Test
    public void downloadFilesTest() {
        FTPUtil ftpUtil = new FTPUtil();

        String path = "";

        String savePath = "C:\\Users\\Administrator\\Desktop";

        ftpUtil.downloadFiles(path, savePath);

    }
}
