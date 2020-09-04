package pers.mihao.toolset.auth.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.mihao.toolset.auth.dao.RolePermissionRelationDao;
import pers.mihao.toolset.auth.entity.RolePermission;
import pers.mihao.toolset.auth.service.RolePermissionService;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author mihao
 * @since 2019-08-10
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionRelationDao, RolePermission> implements RolePermissionService {
}
