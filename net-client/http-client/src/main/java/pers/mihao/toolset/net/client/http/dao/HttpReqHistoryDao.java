package pers.mihao.toolset.net.client.http.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Select;
import pers.mihao.toolset.net.client.http.entity.HttpReqHistory;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2020-01-05
 */
public interface HttpReqHistoryDao extends BaseMapper<HttpReqHistory> {

    @Select("select * from `http_req_history` where `user_id` = #{userId}")
    List<HttpReqHistory> getUserItemByStatus(Integer userId);

    @Select("SELECT * from `http_req_history` where `user_id` = #{userId} and url like '%${filter}%' ORDER BY create_date")
    List<HttpReqHistory> getUserHistoryAndFilter(String filter, Integer userId);
}
