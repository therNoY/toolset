package pers.mihao.toolset.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import pers.mihao.toolset.dto.RegisterDto;
import pers.mihao.toolset.auth.entity.Permission;
import pers.mihao.toolset.auth.entity.User;

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
