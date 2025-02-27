package com.breze.security.handler;

import cn.hutool.json.JSONUtil;
import com.breze.common.consts.CharsetConstant;
import com.breze.common.consts.SystemConstant;
import com.breze.common.result.Result;
import com.breze.config.JwtConfig;
import com.breze.utils.JwtUtil;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author tylt6688
 * @Date 2022/2/10 23:33
 * @Description 登录认证成功处理器
 * @Copyright(c) 2022 , 青枫网络工作室
 */

@Log4j2
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        response.setContentType(CharsetConstant.JSON_TYPE);

        response.setCharacterEncoding(CharsetConstant.UTF_8);

        @Cleanup ServletOutputStream outputStream = response.getOutputStream();

        String username = authentication.getName();

        // 生成JWT放置到响应Header头中
        String jwt = jwtUtil.generateToken(username);

        response.setHeader(jwtConfig.getHeader(), jwt);

        Result<String> result = Result.createSuccessMessage(SystemConstant.LOGIN_SUCCESS);

        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));

    }

}
