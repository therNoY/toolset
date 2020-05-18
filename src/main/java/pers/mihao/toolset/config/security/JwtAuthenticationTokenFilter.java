package pers.mihao.toolset.config.security;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.mihao.toolset.common.constant.AppConstant;
import pers.mihao.toolset.common.util.AuthUtil;
import pers.mihao.toolset.common.util.JwtTokenHelper;
import pers.mihao.toolset.common.util.RespHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * JWT登录授权过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    /* 需要验证的path */
    private List verifyPaths = Arrays.asList(AppConstant.API, AppConstant.ADMIN, AppConstant.ROOT);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // 1.判断如果是OPTIONS 请求直接掉过
        if (request.getMethod().equals(RequestMethod.OPTIONS.toString())) {
            chain.doFilter(request, response);
            return;
        }

        // 1.1 判断如果是不需要验证身份的就跳过
        if (!request.getRequestURI().startsWith(AppConstant.API)
                && !request.getRequestURI().startsWith(AppConstant.ADMIN)
                && !request.getRequestURI().startsWith(AppConstant.ROOT)) {
            chain.doFilter(request, response);
            return;
        }

        // 2. 首先看请求头中是否有Token
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            // 3. 判断token是否过期 如果token 没有过期就设置 信息到Spring Security 上下文中
            if (jwtTokenHelper.validateToken(authToken)) {
                String username = jwtTokenHelper.getUserNameFromToken(authToken);
                SecurityContext securityContext = SecurityContextHolder.getContext();
                if (username != null && securityContext.getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(authentication);
                }
            }else {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().println(JSON.toJSON(RespHelper.error(40003)));
                response.getWriter().flush();
                return;
            }
        }
        chain.doFilter(request, response);

        // 保证清除
        AuthUtil.clear();
    }
}
