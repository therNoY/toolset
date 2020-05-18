package pers.mihao.toolset.auth.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.mihao.toolset.auth.dao.PermissionDao;
import pers.mihao.toolset.auth.entity.Permission;
import pers.mihao.toolset.auth.service.PermissionService;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author mihao
 * @since 2019-08-10
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {

}
