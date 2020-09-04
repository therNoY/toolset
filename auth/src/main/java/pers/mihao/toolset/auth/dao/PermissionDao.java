package pers.mihao.toolset.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import pers.mihao.toolset.auth.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author mihao
 * @since 2019-08-10
 */
public interface PermissionDao extends BaseMapper<Permission> {

    @Select("select name,value from permission p,role_permission rp WHERE p.id = rp.permission_id and rp.role_id in" +
            "(SELECT r.id from role r,user_role ur where r.id = ur.role_id and ur.user_id = #{id})")
    List<Permission> getPermissionByUserId(Integer id);
}
