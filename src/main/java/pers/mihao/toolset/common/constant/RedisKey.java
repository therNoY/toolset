package pers.mihao.toolset.common.constant;

public interface RedisKey {
    // 用户信息
    String USER_INFO = "userInfo";
    String USER_INFO_ = "userInfo::";

    String HTTP_COMMON_HEADERS = "httpCommonHeaders";
    // 用户保存的item
    String USER_POSTMAN_SAVE_ITEM = "userPostmanSaveItem";
    // 用户收藏
    String USER_POSTMAN_COLLECTION = "userPostmanCollection";
    // 用户
    String USER_POSTMAN_HISTORY = "userPostmanHistory";
    String USER_POSTMAN_HISTORY_ = "userPostmanHistory::";

    String USER_POSTMAN_EDIT = "userEdit"; // 用户正在编辑的item
}
