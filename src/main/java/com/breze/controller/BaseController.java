package com.breze.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breze.common.consts.GlobalConstant;
import com.breze.common.enums.ErrorEnum;
import com.breze.config.BrezeConfig;
import com.breze.config.OSSConfig;
import com.breze.service.portal.*;
import com.breze.service.rbac.*;
import com.breze.service.syslog.HandleLogService;
import com.breze.service.syslog.LoginLogService;
import com.breze.service.tool.MailService;
import com.breze.service.tool.QiNiuService;
import com.breze.utils.RedisUtil;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.ServletRequestUtils;
import com.breze.common.result.Result;
import javax.servlet.http.HttpServletRequest;

import static com.breze.common.result.Result.createFailMessage;
import static com.breze.common.result.Result.createSuccessMessage;


/**
 * @Author tylt6688
 * @Date 2022/3/2 22:08
 * @Description Controller 基类，其它 Controller 都需继承此类，公共服务组件在此集体注入，方便后续统一调用
 * @Copyright(c) 2022 , 青枫网络工作室
 */

public class BaseController {

    /**
     * 配置类注入
     */
    @Autowired
    protected BrezeConfig brezeConfig;
    @Autowired
    protected OSSConfig ossConfig;

    //===============================Breze=================================//

    /**
     * 工具类注入
     */
    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    protected RedisUtil redisUtil;

    //===============================Breze=================================//

    /**
     * RabbitMQ 队列服务注入
     */
    @Autowired
    protected Producer producer;

    //===============================Breze=================================//

    /**
     * 通用型服务注入
     */
    @Autowired
    protected QiNiuService qiNiuService;
    @Autowired
    protected MailService mailService;

    //===============================Breze=================================//

    /**
     * 系统日志类注入
     */
    @Autowired
    protected LoginLogService loginLogService;
    @Autowired
    protected HandleLogService handleLogService;

    //===============================Breze=================================//

    /**
     * RBAC权限服务注入
     */
    @Autowired
    protected UserService userService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected MenuService menuService;
    @Autowired
    protected UserRoleService userRoleService;
    @Autowired
    protected RoleMenuService roleMenuService;
    @Autowired
    protected GroupService groupService;
    @Autowired
    protected JobService jobService;
    @Autowired
    protected GroupJobService groupJobService;
    @Autowired
    protected UserGroupJobService userGroupJobService;
    @Autowired
    protected DictService dictService;
    //===============================Breze=================================//

    /**
     * Portal门户服务注入
     */
    @Autowired
    protected BannerService bannerService;
    @Autowired
    protected NavbarService navbarService;
    @Autowired
    protected MainContentService mainContentService;
    @Autowired
    protected ModeCardService modeCardService;
    @Autowired
    protected OssFileService ossFileService;

    //===============================Breze=================================//

    /**
     * MyBatis-Plus分页方法自定义
     */
    @Autowired
    HttpServletRequest request;

    /**
     * 自定义泛型分页数据方法,用于拦截请求参数进行查询显示
     */
    public <T> Page<T> getPage() {
        // 当前页的页码，默认第 1 页
        int current = ServletRequestUtils.getIntParameter(request, "current", GlobalConstant.CURRENT);
        // 每页显示的条数，默认每页 10 条数据
        int size = ServletRequestUtils.getIntParameter(request, "size", GlobalConstant.SIZE);

        return new Page<>(current, size);
    }

    /**
     * 评判 Boolean 类型进行统一结果返回方法,主要用于更新、删除、插入操作
     */
    public Result<String> judgeResult(Boolean flag) {
        return Boolean.TRUE.equals(flag) ? createSuccessMessage() : createFailMessage(ErrorEnum.FindException);
    }

}