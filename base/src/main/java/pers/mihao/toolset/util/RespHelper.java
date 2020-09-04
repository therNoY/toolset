package pers.mihao.toolset.util;


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import pers.mihao.toolset.config.ErrorCode;
import pers.mihao.toolset.dto.RespResult;

import java.util.HashMap;
import java.util.Map;

public class RespHelper {

    @Autowired
    ErrorCode errorCode;

    /**
     * 返回默认的操作成功
     * @return
     */
    public static RespResult success() {
        return new RespResult(0,"ok");
    }

    /**
     * 返回有信息的结果
     * @param object
     * @return
     */
    public static RespResult success(Object object) {
        return new RespResult(0,"ok",object);
    }

    /**
     * 返回两个信息的结果
     * @param object
     * @return
     */
    public static RespResult success(String key1, Object object, String key2, Object object2) {
        Map<String, Object> map = new HashMap<>();
        map.put(key1, object);
        map.put(key2, object2);
        return new RespResult(0,"ok",map);
    }

    /**
     * 返回有信息的结果
     * @param page
     * @return
     */
    public static RespResult successPageResJson (IPage page) {
        Map map = new HashMap();
        map.put("pageCount", page.getTotal());
        map.put("data", page.getRecords());
        return new RespResult(0,"ok",map);
    }


    /**
     * 返回有错误码和错误原因的结果
     * @param mes
     * @return
     */
    public static RespResult error(String mes) {
        return new RespResult(-1, mes);
    }

    /**
     * 返回有错误码和错误原因的结果
     * @param errCode
     * @return
     */
    public static RespResult error(Integer errCode) {
        return new RespResult(errCode, ErrorCode.getErrorMes(errCode));
    }

    /**
     * 参数错误的统一返回类
     * @return
     */
    public static RespResult parsErrResJson (String mess) {
        return new RespResult(40010, mess);
    }

    /**
     * 参数错误的统一返回类
     * @return
     */
    public static RespResult parsErrResJson () {
        return new RespResult(40010, "参数错误");
    }
}
