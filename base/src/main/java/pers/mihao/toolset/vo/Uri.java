package pers.mihao.toolset.vo;

import pers.mihao.toolset.util.Validate;

import java.util.LinkedHashMap;
import java.util.Map;

public class Uri {
    private String url;
    private String domain; // 域名
    private Map<String, String> paramMap;

    public Uri(String url) {
        this.url = url;
        paramMap = new LinkedHashMap<>();
        analyze(url);
    }

    /**
     * 解析URL 如果有参数 将URL解析成域名 + 参数
     * @param uri
     */
    private void analyze(String uri) {
        String [] mess = uri.split("\\?");
        if (mess.length == 1) {
            if (Validate.isUrl(uri))
            this.url = uri;
            return;
        }
        if (mess.length > 2)
            throw new MyException("URL 格式异常");
        this.url = mess[0];

        String [] params = mess[1].split("&");
        for (String param : params) {
            String[] parMess = param.split("=");
            if (parMess.length == 2) {
                paramMap.put(parMess[0], parMess[1]);
            }else if (parMess.length == 1) {
                paramMap.put(parMess[0], "");
            }else {
                throw new MyException("URL 格式异常");
            }
        }
    }

    public String getDomain() {
        return domain;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    /**
     * 获取完整的URI
     * @return
     */
    public String getUri() {
        StringBuffer uri = new StringBuffer(url);
        boolean isFirstAppend = true;
        for (Map.Entry entry : paramMap.entrySet()) {
            if (isFirstAppend) {
                uri.append("?");
                isFirstAppend = false;
            }else {
                uri.append("&");
            }
            uri.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return uri.toString();
    }


    public Uri addParams(String key, String value) {
        paramMap.put(key, value);
        return this;
    }

    public Uri addParams(String key, Integer value) {
        paramMap.put(key, String.valueOf(value));
        return this;
    }

    public void delParams(String key) {
        paramMap.remove(key);
    }

    public String getParams (String key) {
        return paramMap.get(key);
    }

    @Override
    public String toString() {
        return getUri();
    }
}
