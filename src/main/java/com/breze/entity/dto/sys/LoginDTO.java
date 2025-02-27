package com.breze.entity.dto.sys;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author tylt6688
 * @Date 2022/9/16 14:36
 * @Description 登录数据 DTO
 * @Copyright(c) 2022 , 青枫网络工作室
 */
@ApiModel(description = "登录数据传输对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LoginDTO {

    private String username;

    private String password;

    private String key;

    private String code;
}
