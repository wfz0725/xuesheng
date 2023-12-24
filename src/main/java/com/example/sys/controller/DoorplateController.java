package com.example.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.Result;
import com.example.sys.entity.Data;
import com.example.sys.entity.Doorplate;
import com.example.sys.service.IDataService;
import com.example.sys.service.IDoorplateService;
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
@RequestMapping("/doorplate")
public class DoorplateController {
    @Autowired
    private IDoorplateService doorplateService;

    @GetMapping("/list")
    public Result<Map<String,Object>> getDoorplateList(@RequestParam(value = "dNumber",required = false)String dNumber,
                                                  @RequestParam(value = "dDoor",required = false) String dDoor,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Doorplate> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(dNumber),Doorplate::getdNumber,dNumber);
        wrapper.eq(StringUtils.hasLength(dDoor),Doorplate::getdDoor,dDoor);
        wrapper.orderByDesc(Doorplate::getId);

        Page<Doorplate> page=new Page<>(pageNo,pageSize);
        doorplateService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addDoorplate(@RequestBody Doorplate doorplate){
        doorplateService.save(doorplate);
        return Result.success("新增宿舍号成功");
    }

    @PutMapping
    public Result<?> updateDoorplate(@RequestBody Doorplate doorplate){
        doorplateService.updateById(doorplate);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<Doorplate> getDoorplateById(@PathVariable("id") Integer id){
        Doorplate doorplate = doorplateService.getById(id);
        return Result.success(doorplate);
    }

    @DeleteMapping("/{id}")
    public Result<Doorplate> DeleteDoorplateById(@PathVariable("id") Integer id){
        doorplateService.removeById(id);
        return Result.success("删除用户成功");
    }
}
