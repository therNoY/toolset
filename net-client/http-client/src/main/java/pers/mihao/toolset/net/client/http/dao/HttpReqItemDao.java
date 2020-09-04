package pers.mihao.toolset.net.client.http.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pers.mihao.toolset.common.annotation.KnowledgePoint;
import pers.mihao.toolset.net.client.http.entity.HttpReqItem;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2020-01-05
 */
public interface HttpReqItemDao extends BaseMapper<HttpReqItem> {

    @KnowledgePoint("mybatis注解中使用for循环， 需要加上<script>")
    @Select({
            "<script>",
                "select * from `http_req_item`",
                "where `user_id` = #{userId} and status in",
                "<foreach collection='status' item='s' open='(' separator=',' close=')'>",
                    "#{s}",
                "</foreach>",
            "</script>"
    })
    List<HttpReqItem> getUserItemByStatus(Integer userId, Integer... status);

    @Select("SELECT * from `http_req_item` where `user_id` = #{userId} and url like '%${filter}%' and status = 1 ORDER BY create_date")
    List<HttpReqItem> getUserHistoryAndFilter(String filter, Integer userId);

    @Update("update `http_req_item` set status = #{status} where id = #{id}")
    void updateStatus(Integer id, Integer status);

    @Delete("delete from `http_req_item` where user_id = #{authId} and collections_id = #{id}")
    void deleteByCollectId(Integer authId, Integer id);
}
