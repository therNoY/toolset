package pers.mihao.toolset.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import pers.mihao.toolset.auth.entity.User;
import pers.mihao.toolset.common.BaseDAO;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author mihao
 * @since 2019-08-10
 */
public interface UserDao extends BaseMapper<User> {

}
