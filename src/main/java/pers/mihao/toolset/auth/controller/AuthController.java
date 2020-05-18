package pers.mihao.toolset.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pers.mihao.toolset.auth.dto.RegisterDto;
import pers.mihao.toolset.auth.dto.RespAuthDao;
import pers.mihao.toolset.auth.dto.TokenDto;
import pers.mihao.toolset.auth.entity.User;
import pers.mihao.toolset.auth.service.UserService;
import pers.mihao.toolset.common.util.JwtTokenHelper;
import pers.mihao.toolset.common.util.RedisHelper;
import pers.mihao.toolset.common.util.RespHelper;
import pers.mihao.toolset.common.vo.RespResult;
import pers.mihao.toolset.auth.dto.LoginDto;
import pers.mihao.toolset.email.service.EmailService;

import java.util.UUID;

@RestController
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    RedisHelper redisHelper;
    @Autowired
    EmailService emailService;

    Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 用户登录
     * @param loginDto
     * @param result
     * @return
     */
    @PostMapping("/login")
    public RespResult getLoginInfo(@RequestBody @Validated LoginDto loginDto, BindingResult result){
        User loginUser = userService.getUserByNameOrEmail(loginDto.getUserName());
        String token;
        if (loginUser != null && passwordEncoder.matches(loginDto.getPassword(), loginUser.getPassword())) {
            token = jwtTokenHelper.generateToken(loginDto.getUserName());
            return RespHelper.success(new RespAuthDao(loginUser, token));
        }
        return RespHelper.error(40011);
    }


    /**
     * 用户请求注册
     * @param registerDto
     * @param result
     * @return
     */
    @PostMapping("/register")
    public RespResult userRegister(@RequestBody @Validated RegisterDto registerDto, BindingResult result) {
        // 先验证邮箱是否存在 再验证用户是否存在
        User userByEmail = userService.getUserByEmail(registerDto.getEmail());
        if (userByEmail != null) {
            log.error("用户注册错误 邮箱 {} 已注册", userByEmail.getName());
            return RespHelper.error(40013);
        }
        User user = userService.getUserByName(registerDto.getUsername());
        if (user != null) {
            log.error("用户注册错误 用户名 {} 重复", user.getName());
            return RespHelper.error(40012);
        }
        // 准备发送给邮件服务器
        // 1.获取token
        String uuid = UUID.randomUUID().toString();
        // 2.发送邮件
        emailService.sendRegisterEmail(registerDto, uuid);
        // 3.放到缓存中 key email+_REGISTER 时间60s
        redisHelper.set(uuid, registerDto, 600l);
        return RespHelper.success();
    }

    /**
     * 用户通过邮箱确认注册的api
     */
    @GetMapping("/register/confirm")
    public ModelAndView registerCallback(@RequestParam String token) {
        RegisterDto registerDto = null;
        if ((registerDto = redisHelper.getObject(token, RegisterDto.class)) != null) {
            userService.saveRegister(registerDto);
            return new ModelAndView("registerSuccess");
        }else {
            return new ModelAndView("registerErr");
        }
    }

    /**
     * 通过token 获取用信息
     * @param token
     * @param result
     * @return
     */
    @PostMapping("/userInfo")
    public RespResult getUserInfoByToken(@RequestBody @Validated TokenDto token, BindingResult result) {
        boolean isRight = jwtTokenHelper.validateToken(token.getToken());
        if (isRight) {
            String userName = jwtTokenHelper.getUserNameFromToken(token.getToken());
            User userInfo = userService.getUserByName(userName);
            if (userInfo != null) {
                return RespHelper.success(userInfo);
            }
        }
        return RespHelper.error(40003);
    }
}
