package pers.mihao.toolset.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.mihao.toolset.auth.dto.RegisterDto;
import pers.mihao.toolset.auth.entity.Permission;
import pers.mihao.toolset.auth.entity.User;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author mihao
 * @since 2019-08-10
 */
public interface UserService extends IService<User> {

    User getUserByNameOrEmail(String username);

    List<Permission> getPermissionList(Integer id);

    User getUserByEmail(String email);

    User getUserByName(String userName);

    void saveRegister(RegisterDto registerDto);
}
