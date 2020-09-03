package pers.mihao.toolset.client.net.http.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Select;
import pers.mihao.toolset.client.net.http.entity.PostmanHistory;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2020-01-05
 */
public interface PostmanHistoryDao extends BaseMapper<PostmanHistory> {

    @Select("select * from `postman_history` where `user_id` = #{userId}")
    List<PostmanHistory> getUserItemByStatus(Integer userId);

    @Select("SELECT * from `postman_history` where `user_id` = #{userId} and url like '%${filter}%' ORDER BY create_date")
    List<PostmanHistory> getUserHistoryAndFilter(String filter, Integer userId);
}
