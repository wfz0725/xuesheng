package com.example.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.Result;
import com.example.sys.entity.Builder;
import com.example.sys.entity.Health;
import com.example.sys.service.IHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping ("/health")
public class HealthController {
    @Autowired
    private IHealthService healthService;

    @GetMapping("/list")
    public Result<Map<String,Object>> getHealthList(@RequestParam(value = "dNumber",required = false)String dNumber,
                                                    @RequestParam(value = "status",required = false) String status,
                                                    @RequestParam(value = "pageNo") Long pageNo,
                                                    @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Health> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(dNumber), Health::getdNumber,dNumber);
        wrapper.eq(StringUtils.hasLength(status),Health::getStatus,status);
        wrapper.orderByDesc(Health::getId);

        Page<Health> page=new Page<>(pageNo,pageSize);
        healthService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addHealth(@RequestBody Health health){
        healthService.save(health);
        return Result.success("新增问题成功");
    }

    @PutMapping
    public Result<?> updateHealth(@RequestBody Health health){
        healthService.updateById(health);
        return Result.success("修改问题成功");
    }

    @GetMapping("/{id}")
    public Result<Health> getHealthById(@PathVariable("id") Integer id){
        Health health = healthService.getById(id);
        return Result.success(health);
    }

    @DeleteMapping("/{id}")
    public Result<Health> DeleteHealthById(@PathVariable("id") Integer id){
        healthService.removeById(id);
        return Result.success("删除用户成功");
    }
}
