package com.example.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.Result;
import com.example.sys.entity.Cleaner;
import com.example.sys.entity.User;
import com.example.sys.service.ICleanerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

import static com.baomidou.mybatisplus.extension.toolkit.Db.page;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-12-12
 */
@RestController
@RequestMapping("/cleaner")
public class CleanerController {
    @Autowired
    private ICleanerService cleanerService;

    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value = "cName",required = false)String cName,
                                                  @RequestParam(value = "cId",required = false) String cId,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Cleaner> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(cName),Cleaner::getcName,cName);
        wrapper.eq(StringUtils.hasLength(cId),Cleaner::getcId,cId);
        wrapper.orderByDesc(Cleaner::getId);

       Page<Cleaner> page=new Page<>(pageNo,pageSize);
       cleanerService.page(page,wrapper);

       Map<String,Object> data=new HashMap<>();
       data.put("total",page.getTotal());
       data.put("rows",page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addCleaner(@RequestBody Cleaner cleaner){
        cleanerService.save(cleaner);
        return Result.success("新增用户成功");
    }

    @PutMapping
    public Result<?> updateCleaner(@RequestBody Cleaner cleaner){
        cleanerService.updateById(cleaner);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<Cleaner> getCleanerById(@PathVariable("id") Integer id){
        Cleaner cleaner = cleanerService.getById(id);
        return Result.success(cleaner);
    }

    @DeleteMapping("/{id}")
    public Result<Cleaner> DeleteCleanerById(@PathVariable("id") Integer id){
        cleanerService.removeById(id);
        return Result.success("删除用户成功");
    }

}
