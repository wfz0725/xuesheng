package com.example.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.Result;
import com.example.sys.entity.Data;
import com.example.sys.entity.Information;
import com.example.sys.service.IDataService;
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
@RequestMapping("/data")
public class DataController {
    @Autowired
    private IDataService dataService;

    @GetMapping("/list")
    public Result<Map<String,Object>> getDataList(@RequestParam(value = "sName",required = false)String sName,
                                                         @RequestParam(value = "sId",required = false) String sId,
                                                         @RequestParam(value = "pageNo") Long pageNo,
                                                         @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Data> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(sName),Data::getsName,sName);
        wrapper.eq(StringUtils.hasLength(sId),Data::getsId,sId);
        wrapper.orderByDesc(Data::getId);

        Page<Data> page=new Page<>(pageNo,pageSize);
        dataService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addData(@RequestBody Data data){
        dataService.save(data);
        return Result.success("新增用户成功");
    }

    @PutMapping
    public Result<?> updateData(@RequestBody Data data){
        dataService.updateById(data);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<Data> getDataById(@PathVariable("id") Integer id){
        Data data = dataService.getById(id);
        return Result.success(data);
    }

    @DeleteMapping("/{id}")
    public Result<Data> DeleteDataById(@PathVariable("id") Integer id){
        dataService.removeById(id);
        return Result.success("删除用户成功");
    }
}
