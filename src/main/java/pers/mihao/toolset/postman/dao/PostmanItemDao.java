package pers.mihao.toolset.postman.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pers.mihao.toolset.common.annotation.KnowledgePoint;
import pers.mihao.toolset.postman.entity.PostmanItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2020-01-05
 */
public interface PostmanItemDao extends BaseMapper<PostmanItem> {

    @KnowledgePoint("mybatis注解中使用for循环， 需要加上<script>")
    @Select({
            "<script>",
                "select * from `postman_item`",
                "where `user_id` = #{userId} and status in",
                "<foreach collection='status' item='s' open='(' separator=',' close=')'>",
                    "#{s}",
                "</foreach>",
            "</script>"
    })
    List<PostmanItem> getUserItemByStatus(Integer userId, Integer... status);

    @Select("SELECT * from `postman_item` where `user_id` = #{userId} and url like '%${filter}%' and status = 1 ORDER BY create_date")
    List<PostmanItem> getUserHistoryAndFilter(String filter, Integer userId);

    @Update("update `postman_item` set status = #{status} where id = #{id}")
    void updateStatus(Integer id, Integer status);

    @Delete("delete from `postman_item` where user_id = #{authId} and collections_id = #{id}")
    void deleteByCollectId(Integer authId, Integer id);
}
