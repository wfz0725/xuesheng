package com.example.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.Result;
import com.example.sys.entity.Cleaner;
import com.example.sys.entity.Information;
import com.example.sys.service.ICleanerService;
import com.example.sys.service.IInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-12-12
 */
@RestController
@RequestMapping("/information")
public class InformationController {
    @Autowired
    private IInformationService informationService;

    @GetMapping("/list")
    public Result<Map<String,Object>> getInformationList(@RequestParam(value = "sName",required = false)String sName,
                                                  @RequestParam(value = "sPhone",required = false) String sPhone,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Information> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(sName),Information::getsName,sName);
        wrapper.eq(StringUtils.hasLength(sPhone),Information::getsPhone,sPhone);
        wrapper.orderByDesc(Information::getId);

        Page<Information> page=new Page<>(pageNo,pageSize);
        informationService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addInformation(@RequestBody Information information){
        informationService.save(information);
        return Result.success("新增用户成功");
    }

    @PutMapping
    public Result<?> updateInformation(@RequestBody Information information){
        informationService.updateById(information);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<Information> getInformationById(@PathVariable("id") Integer id){
        Information information = informationService.getById(id);
        return Result.success(information);
    }

    @DeleteMapping("/{id}")
    public Result<Information> DeleteInformationById(@PathVariable("id") Integer id){
        informationService.removeById(id);
        return Result.success("删除用户成功");
    }
}
