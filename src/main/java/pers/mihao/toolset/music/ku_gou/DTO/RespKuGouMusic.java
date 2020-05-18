package pers.mihao.toolset.music.ku_gou.DTO;


import com.alibaba.fastjson.JSONObject;

/**
 * {
 *     "fileHead": 100,
 *     "q": 0,
 *     "extra": {
 *         "320filesize": 12532729,
 *         "sqfilesize": 35187088,
 *         "sqhash": "42D7B611F6FC7FD96FF4FA7DCB2AC5C7",
 *         "128hash": "BAF55E2BBAB2B0DA459BF0EF176066F0",
 *         "320hash": "50A9A1007E7FD4F6F8FD011940F1A483",
 *         "128filesize": 5012060
 *     },
 *     "fileSize": 5012060,
 *     "choricSinger": "张靓颖",
 *     "album_img": "http://imge.kugou.com/stdmusic/{size}/20160908/20160908110650419032.jpg",
 *     "topic_remark": "",
 *     "url": "http://fs.open.kugou.com/0307e2fb6b4ec7bac3abbe58f7645796/5d1386fa/G008/M03/1D/11/SA0DAFUMY_qANEy7AEx6XKxhr8s748.mp3",
 *     "time": 1561560826,
 *     "trans_param": {
 *         "cid": 6106733,
 *         "pay_block_tpl": 1,
 *         "musicpack_advance": 0,
 *         "display_rate": 0,
 *         "display": 0
 *     },
 *     "albumid": 1778299,
 *     "singerName": "张靓颖",
 *     "topic_url": "",
 *     "extName": "mp3",
 *     "songName": "天下无双",
 *     "singerHead": "",
 *     "hash": "50A9A1007E7FD4F6F8FD011940F1A483",
 *     "intro": "",
 *     "store_type": "audio",
 *     "error": "",
 *     "imgUrl": "http://singerimg.kugou.com/uploadpic/softhead/{size}/20170628/20170628110356447.jpg",
 *     "album_audio_id": 39913227,
 *     "area_code": "1",
 *     "bitRate": 128,
 *     "128privilege": 8,
 *     "req_hash": "BAF55E2BBAB2B0DA459BF0EF176066F0",
 *     "status": 1,
 *     "stype": 11323,
 *     "mvhash": "",
 *     "320privilege": 10,
 *     "privilege": 8,
 *     "singerId": 6808,
 *     "ctype": 1009,
 *     "fileName": "张靓颖 - 天下无双",
 *     "errcode": 0,
 *     "sqprivilege": 10,
 *     "backup_url": [
 *         "http://fs.open2.kugou.com/201906262253/0786059a0079d56ff0bfcb50aea0af2f/G008/M03/1D/11/SA0DAFUMY_qANEy7AEx6XKxhr8s748.mp3"
 *     ],
 *     "timeLength": 313
 * }
 */

public class RespKuGouMusic {
    private JSONObject extra; // 扩展
    private String fileSize;
    private String choricSinger;
    private String albumImg;
    private String url;
    private String time;
    private String albumid;
    private String singerName;
    private String extName;
    private String songName;
    private String hash;
    private String imgUrl;
    private String albumAudioId;
    private String reqHash;
    private String singerId;
    private String fileName;
    private String[] backupUrl;


    public JSONObject getExtra() {
        return extra;
    }

    public void setExtra(JSONObject extra) {
        this.extra = extra;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getChoricSinger() {
        return choricSinger;
    }

    public void setChoricSinger(String choricSinger) {
        this.choricSinger = choricSinger;
    }

    public String getAlbumImg() {
        return albumImg;
    }

    public void setAlbumImg(String albumImg) {
        this.albumImg = albumImg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAlbumid() {
        return albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAlbumAudioId() {
        return albumAudioId;
    }

    public void setAlbumAudioId(String albumAudioId) {
        this.albumAudioId = albumAudioId;
    }

    public String getReqHash() {
        return reqHash;
    }

    public void setReqHash(String reqHash) {
        this.reqHash = reqHash;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getBackupUrl() {
        return backupUrl;
    }

    public void setBackupUrl(String[] backupUrl) {
        this.backupUrl = backupUrl;
    }
}
