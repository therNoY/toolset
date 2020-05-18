package pers.mihao.toolset.postman.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import pers.mihao.toolset.postman.entity.PostmanCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-02-06
 */
public interface PostmanCollectionDao extends BaseMapper<PostmanCollect> {

    @Select("SELECT * from postman_collect where user_id = #{userId}")
    List<PostmanCollect> getByUserId(Integer userId);

    @Delete("delete from postman_collect where id = #{id} or history_id = #{id}")
    void deleteAllCollect(Integer id);
}
