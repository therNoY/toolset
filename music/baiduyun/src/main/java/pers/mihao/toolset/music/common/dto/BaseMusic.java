package pers.mihao.toolset.music.common.dto;

/**
 * 基础的音乐DTO
 */
public abstract class BaseMusic {
    private String uuid;
    private String name;
    private BaseSinger singer;
    private String extName;
    private String cover; // 歌曲封面
    private String url;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseSinger getSinger() {
        return singer;
    }

    public void setSinger(BaseSinger singer) {
        this.singer = singer;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
