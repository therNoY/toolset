package pers.mihao.toolset.music.seed.dto;


/**
 * 从盘搜搜主页获取的 盘搜搜的资源连接
 */
public class ResourceDTO {

    public ResourceDTO(String url, String name) {
        this.url = url;
        this.name = name;
    }

    private String url;
    private String name;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
