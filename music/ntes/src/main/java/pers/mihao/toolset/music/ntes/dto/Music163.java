package pers.mihao.toolset.music.ntes.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * 163 获取的音乐
 */
public class Music163 implements Serializable {

    @TableId(value = "song_id", type = IdType.INPUT)
    private String songId;
    private String title;
    private String images;
    private String author;
    private String album;
    private String description;
    private String mp3Url;
    private String publishedDate;
    private String commentId;
    private String commentUserId;
    private String commentNickname;
    private String commentAvatarUrl;
    private String commentLikedCount;
    private String commentContent;
    private String commentPublishedDate;

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentNickname() {
        return commentNickname;
    }

    public void setCommentNickname(String commentNickname) {
        this.commentNickname = commentNickname;
    }

    public String getCommentAvatarUrl() {
        return commentAvatarUrl;
    }

    public void setCommentAvatarUrl(String commentAvatarUrl) {
        this.commentAvatarUrl = commentAvatarUrl;
    }

    public String getCommentLikedCount() {
        return commentLikedCount;
    }

    public void setCommentLikedCount(String commentLikedCount) {
        this.commentLikedCount = commentLikedCount;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentPublishedDate() {
        return commentPublishedDate;
    }

    public void setCommentPublishedDate(String commentPublishedDate) {
        this.commentPublishedDate = commentPublishedDate;
    }
}
