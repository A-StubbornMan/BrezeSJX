package com.breze.mapper.rbac;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.breze.entity.pojo.rbac.UserJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author tylt6688
 * @Date 2022/9/25 9:22
 * @Description 用户岗位关联表
 * @Copyright(c) 2022 , 青枫网络工作室
 */
@Mapper
public interface UserJobMapper extends BaseMapper<UserJob> {

}
