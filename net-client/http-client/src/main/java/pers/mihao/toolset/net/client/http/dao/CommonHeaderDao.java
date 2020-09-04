package pers.mihao.toolset.net.client.http.dao;

import org.apache.ibatis.annotations.Select;
import pers.mihao.toolset.net.client.http.entity.CommonHeader;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-01-24
 */
public interface CommonHeaderDao extends BaseMapper<CommonHeader> {

    @Select("SELECT  id,`key`,default_value,default_use,des  FROM common_header")
    List<CommonHeader> findAll();
}
