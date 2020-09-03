package pers.mihao.toolset;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamTest {

    @Test
    public void name() throws IOException {
        InputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\78\\start-mimi-img-server.bat");

        StringBuffer sb = new StringBuffer();
        byte[] bodyRead = new byte[1024];
        while (inputStream.read(bodyRead, 0, 1024) != -1) {
            String s = new String(bodyRead);
            System.out.println(s);
            sb.append(s);
        }

        System.out.println(sb.toString());
        inputStream.close();
    }
}
