package com.breze.controller.portal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breze.common.annotation.BrezeLog;
import com.breze.common.enums.ErrorEnum;
import com.breze.common.result.Result;
import com.breze.controller.BaseController;
import com.breze.converter.portal.NavbarConvert;
import com.breze.entity.pojo.portal.Navbar;
import com.breze.entity.vo.portal.NavbarTitleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 导航连接表 前端控制器
 * </p>
 *
 * @author leochan
 * @since 2022-10-08
 */
@Log4j2
@Api(tags = "门户导航管理")
@RestController
@RequestMapping("/breze/portal/navbar")
public class NavbarController extends BaseController {

    @ApiOperation("分页获取导航标题")
    @BrezeLog("分页获取导航标题")
    @GetMapping("/page")
    public Result findPage(String titleName,Long parentId){
        Page<Navbar> NavbarPage = navbarService.findNavbarPage(getPage(),titleName,parentId);
        return Result.createSuccessMessage("分页获取导航标题成功", NavbarPage);
    }
    @ApiOperation("获取导航标题")
    @BrezeLog("获取导航标题")
    @GetMapping("/select")
    public Result findAllData(Long flag){
        return Result.createSuccessMessage("获取导航标题成功", navbarService.finAllData(flag));
    }

    @ApiOperation("通过id获取导航标题")
    @BrezeLog("通过id获取导航标题")
    @GetMapping("/selectNavbar/{id}")
    public Result selectNavbar(@PathVariable Long id) {
        return Result.createSuccessMessage("获取导航标题成功", navbarService.getById(id));
    }
    @ApiOperation("通过标识获取导航标题（0为头部导航，1为尾部导航）")
    @BrezeLog("通过标识获取导航标题（0为头部导航，1为尾部导航）")
    @GetMapping("/findAllData/{flag}")
    public Result findDataByFlag(@PathVariable Long flag) {
        List<NavbarTitleVo> navbarTitleVos = NavbarConvert.INSTANCE.NavbarListToTitleVo(navbarService.list(new QueryWrapper<Navbar>().eq("flag",flag)));
        return Result.createSuccessMessage("获取导航标题成功", navbarTitleVos);
    }
    @ApiOperation("获取导航标题数量")
    @BrezeLog("获取导航标题数量")
    @GetMapping("/count")
    public Result count() {
        return Result.createSuccessMessage("获取导航数量成功", navbarService.count());
    }

    @ApiOperation("新增导航标题")
    @BrezeLog("新增导航标题")
    @PostMapping("/insert")
    public Result saveMain(@Validated @RequestBody Navbar navbar) {
        return judgeResult(navbarService.save(navbar));
    }

    @ApiOperation("修改导航标题")
    @BrezeLog("修改导航标题")
    @PostMapping("/update")
    public Result updateMain(@Validated @RequestBody Navbar navbar) {
       return judgeResult(navbarService.updateById(navbar));
    }
    @ApiOperation("删除导航标题")
    @BrezeLog("删除导航标题")
    @PostMapping("/delete")
    public Result delete(@RequestBody Long id) {
       return judgeResult(navbarService.removeById(id));
    }


}
