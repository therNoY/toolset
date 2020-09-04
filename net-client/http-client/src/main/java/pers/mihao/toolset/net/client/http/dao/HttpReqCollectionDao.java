package pers.mihao.toolset.net.client.http.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import pers.mihao.toolset.net.client.http.entity.HttpReqCollect;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-02-06
 */
public interface HttpReqCollectionDao extends BaseMapper<HttpReqCollect> {

    @Select("SELECT * from http_req_collect where user_id = #{userId}")
    List<HttpReqCollect> getByUserId(Integer userId);

    @Delete("delete from http_req_collect where id = #{id} or history_id = #{id}")
    void deleteAllCollect(Integer id);
}
