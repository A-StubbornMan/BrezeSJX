package com.breze.controller.rbac;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breze.common.annotation.BrezeLog;
import com.breze.common.result.Result;
import com.breze.controller.BaseController;
import com.breze.converter.sys.GroupConvert;
import com.breze.entity.dto.sys.GroupDTO;
import com.breze.entity.pojo.rbac.Group;
import com.breze.entity.pojo.rbac.GroupJob;
import com.breze.entity.vo.sys.ParentGroupVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author tylt6688
 * @Date 2022-03-25
 * @Discription 部门团队前端控制器
 * @Copyright(c) 2022 , 青枫网络工作室
 */
@Api(tags = "部门团队管理")
@RestController
@RequestMapping("/sys/group")
public class GroupController extends BaseController {

    @ApiOperation(value = "获取全部部门列表")
    @BrezeLog("获取全部部门列表")
    @GetMapping("/select")
    public Result<List<Group>> selectAll(@RequestParam String name) {
        return Result.createSuccessMessage("查询部门成功", groupService.findAll(name));
    }

    @ApiOperation(value = "获取所有父节点")
    @BrezeLog("获取所有父节点")
    @GetMapping("/getGroupParent")
    public Result<List<ParentGroupVO>> getGroupParent() {
        return Result.createSuccessMessage("查询成功", groupService.getGroupParent());
    }
    @ApiOperation(value = "获取单个部门信息")
    @BrezeLog("获取单个部门信息")
    @GetMapping("/select/{id}")
    public Result<Group> selectById(@PathVariable Long id) {
        return Result.createSuccessMessage("查询单个部门成功", groupService.findById(id));
    }

    @ApiOperation(value = "新增部门", notes = "新增部门")
    @BrezeLog("新增部门")
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody GroupDTO groupDTO) {
        Group group = GroupConvert.INSTANCE.groupDTOToGroup(groupDTO);
        if (groupService.insert(group)) {
            return Result.createSuccessMessage("新增部门成功");
        }
        // TODO ERROR替换 [抄送人: LGX 待办人: tylt6688 2023-03-26]
        return Result.createSuccessMessage("部门名称重复");
    }

    // FIXME 更新部门转换id丢失 [抄送人：ChenWX 待办人：LGX 2023-04-12]
    @ApiOperation(value = "更新部门", notes = "更新部门")
    @BrezeLog("更新部门")
    @PutMapping("/update")
    public Result<String> update(@RequestBody GroupDTO groupDTO) {
        Group group = GroupConvert.INSTANCE.groupDTOToGroup(groupDTO);
        if (groupService.update(group)) {
            return Result.createSuccessMessage("更新部门成功");
        }
        // TODO ERROR替换 [抄送人: LGX 待办人: tylt6688 2023-03-26]
        return Result.createSuccessMessage("部门已存在");
    }

    @ApiOperation(value = "删除部门", notes = "删除部门")
    @BrezeLog("删除部门")
    @DeleteMapping("/delete")
    public Result<String> deleteById(@RequestParam Long id) {
        if (0 < groupJobService.count(new QueryWrapper<GroupJob>().eq("group_id", id))) {
            // TODO ERROR替换 [抄送人: LGX 待办人: tylt6688 2023-03-27]
            return Result.createSuccessMessage("删除部门失败--");
        }
        if (0 < groupService.count(new QueryWrapper<Group>().eq("parent_id", id))) {
            // TODO ERROR替换 [抄送人: LGX 待办人: tylt6688 2023-03-27]
            return Result.createSuccessMessage("删除部门失败, 子部门存在");
        }
        groupService.delete(id);
        return Result.createSuccessMessage("删除部门成功");
    }

}
