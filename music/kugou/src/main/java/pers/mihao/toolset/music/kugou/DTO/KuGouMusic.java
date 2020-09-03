package pers.mihao.toolset.music.kugou.DTO;

import java.util.List;

/**
 * 每个网站定义的音乐的pojo都不一样 所以之后都是单独定义
 */
public class KuGouMusic {
    private String albumID; //专辑
    private String fileHash;
    private String albumName; //专辑名称
    private String fileName;
    private String extName;
    private String fileSize;
    private String singerName;
    private Integer duration; // 时长
    private List<Integer> singerId;


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public List<Integer> getSingerId() {
        return singerId;
    }

    public void setSingerId(List<Integer> singerId) {
        this.singerId = singerId;
    }
}
