package pers.mihao.toolset.auth.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pers.mihao.toolset.auth.dao.PermissionDao;
import pers.mihao.toolset.auth.dao.UserDao;
import pers.mihao.toolset.auth.dao.UserRoleRelationDao;
import pers.mihao.toolset.auth.dto.RegisterDto;
import pers.mihao.toolset.auth.entity.Permission;
import pers.mihao.toolset.auth.entity.User;
import pers.mihao.toolset.auth.entity.UserRoleRelation;
import pers.mihao.toolset.auth.service.UserService;
import pers.mihao.toolset.constant.RedisKey;

import java.net.PasswordAuthentication;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author mihao
 * @since 2019-08-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDao userDao;
    @Autowired
    PermissionDao permissionDao;
    @Autowired
    UserRoleRelationDao userRoleRelationDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Cacheable(value = RedisKey.USER_INFO, unless="#result == null")
    public User getUserByNameOrEmail(String username) {
        log.info("query {} from DB", username);
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("name", username)
                .or()
                .eq("email", username);
        return userDao.selectOne(wrapper);
    }

    @Override
    public List<Permission> getPermissionList(Integer id) {
        return permissionDao.getPermissionByUserId(id);
    }

    @Override
    public User getUserByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("email", email);
        return userDao.selectOne(wrapper);
    }

    @Override
    public User getUserByName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("name", userName);
        return userDao.selectOne(wrapper);
    }

    /**
     * 保存注册的信息
     * @param registerDto
     */
    @Override
    public void saveRegister(RegisterDto registerDto) {
        User user = new User();
        user.setName(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setEmail(registerDto.getEmail());
        userDao.insert(user);
        if (user.getId() != null) {
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setUserId(user.getId());
            userRoleRelationDao.insert(userRoleRelation);
            return;
        }
        log.error("插入用户没有返回主键{}", user);
    }
}
