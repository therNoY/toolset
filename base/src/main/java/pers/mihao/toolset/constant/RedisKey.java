package pers.mihao.toolset.constant;

public interface RedisKey {
    // 用户信息
    String USER_INFO = "userInfo";
    String USER_INFO_ = "userInfo::";

    String HTTP_COMMON_HEADERS = "httpCommonHeaders";
    // 用户保存的item
    String USER_HTTP_REQ_SAVE_ITEM = "userHttpReqSaveItem";
    // 用户收藏
    String USER_HTTP_REQ_COLLECTION = "userHttpReqCollection";
    // 用户
    String USER_HTTP_REQ_HISTORY = "userHttpReqHistory";
    String USER_HTTP_REQ_HISTORY_ = "userHttpReqHistory::";

    String USER_HTTP_REQ_EDIT = "userEdit"; // 用户正在编辑的item
}
