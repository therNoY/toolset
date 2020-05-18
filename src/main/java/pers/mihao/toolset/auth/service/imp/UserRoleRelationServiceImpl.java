package pers.mihao.toolset.auth.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.mihao.toolset.auth.dao.UserRoleRelationDao;
import pers.mihao.toolset.auth.entity.UserRoleRelation;
import pers.mihao.toolset.auth.service.UserRoleRelationService;

/**
 * <p>
 * 用户和角色表 服务实现类
 * </p>
 *
 * @author mihao
 * @since 2019-08-10
 */
@Service
public class UserRoleRelationServiceImpl extends ServiceImpl<UserRoleRelationDao, UserRoleRelation> implements UserRoleRelationService {

}
