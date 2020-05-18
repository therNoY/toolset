package pers.mihao.toolset;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

public class ZkTest {
	
    private static String scheme = "digest";

    public ZooKeeper zk = null;

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZkTest zkTest = new ZkTest("10.0.12.78:2181", 5000, "u3cuser", "u3cuser123");

        List<String> stringList = zkTest.zk.getChildren("/Center/Department/101/Global", null);
        String a= zkTest.getNodeData("/Center/Department/101/Global/callout_discon_time", "101");
        zkTest.zk.close();
    }

    public ZkTest(String host, Integer timeout, String userName, String password) {
        connectZk(host, timeout, userName, password);
    }

    public boolean connectZk() {
        return zk != null ? true : false;
    }


    public void connectZk(String host, Integer timeout, String userName, String password) {
        if (zk == null) {
            try {
                this.zk = new ZooKeeper(host, timeout, null);
                if (userName != null && password != null) {
                    this.zk.addAuthInfo(scheme, (userName + ":" + password).getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getNodeData(String path, String defaultValue) {

        byte[] value = null;
        try {
            value = zk.getData(path, true, null);
            return new String(value);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (value == null) {
            value = defaultValue.getBytes();
        }
        return new String(value);
    }
}
