package pers.mihao.toolset.client.net.http.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import pers.mihao.toolset.client.net.http.entity.CommonHeader;

public class RespCommonHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    List<HeaderKey> headerKeyList;

    List<CommonHeader> commonHeaderList;


    public List<HeaderKey> getHeaderKeyList() {
        return headerKeyList;
    }

    public void setHeaderKeyList(List<HeaderKey> headerKeyList) {
        this.headerKeyList = headerKeyList;
    }

    public List<CommonHeader> getCommonHeaderList() {
        return commonHeaderList;
    }

    public void setCommonHeaderList(List<CommonHeader> commonHeaderList) {
        this.commonHeaderList = commonHeaderList;
    }

    public static class HeaderKey implements Serializable{

        private String value;

        public HeaderKey() {
        }

        public HeaderKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static List<HeaderKey> makeHeaderKeys(String... keys) {
        List<HeaderKey> headerKeys = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            headerKeys.add(new HeaderKey(keys[i]));
        }
        return headerKeys;
    }
}
