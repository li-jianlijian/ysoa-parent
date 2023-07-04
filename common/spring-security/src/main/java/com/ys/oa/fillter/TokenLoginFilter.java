package com.ys.oa.fillter;


import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ys.oa.config.redis.RedisConfig;
import com.ys.oa.custom.CustomUser;
import com.ys.oa.jwt.JwtHelper;
import com.ys.oa.result.Result;
import com.ys.oa.result.ResultCodeEnum;
import com.ys.oa.util.ResponseUtil;
import com.ys.oa.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * </p>
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private RedisTemplate redisTemplate;
    public TokenLoginFilter(AuthenticationManager authenticationManager,
                            RedisTemplate redisTemplate) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/admin/system/index/login","POST"));
        this.redisTemplate = redisTemplate;
    }

    /**
     * 登录认证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            // 获取登录对象
            LoginVo loginVo = new ObjectMapper().readValue(req.getInputStream(), LoginVo.class);

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登录成功
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // 获取当前登录用户
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        // 生成token
        String token = JwtHelper.createToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());

        // 权限存入redis
        redisTemplate.opsForValue().set("username:"+customUser.getUsername(),
                JSON.toJSONString(customUser.getAuthorities()));
        // 封装返回值
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        ResponseUtil.out(response, Result.ok(map));
    }

    /**
     * 登录失败
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        System.out.println(e.getClass());
        // 默认情况springSecurity关闭了UsernameNotFoundException,需要daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        if(e.getClass().equals(UsernameNotFoundException.class)){
            //此时异常属于用户名不存在
            ResponseUtil.out(response, Result.fail(ResultCodeEnum.LOGIN_FAIL,"用户名不存在或被停用"));
        }else {
            //此时异常属于密码错误
            ResponseUtil.out(response, Result.fail(ResultCodeEnum.LOGIN_FAIL,"密码错误"));
        }


    }
}