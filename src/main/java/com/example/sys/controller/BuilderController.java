package com.example.sys.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.Result;
import com.example.sys.entity.Builder;
import com.example.sys.service.IBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


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
@RequestMapping("/builder")
public class BuilderController {
    @Autowired
    private IBuilderService builderService;

    @GetMapping("/list")
    public Result<Map<String,Object>> getBuilderList(@RequestParam(value = "dNumber",required = false)String dNumber,
                                                  @RequestParam(value = "cAunt",required = false) String cAunt,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Builder> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(dNumber),Builder::getdNumber,dNumber);
        wrapper.eq(StringUtils.hasLength(cAunt),Builder::getcAunt,cAunt);
        wrapper.orderByDesc(Builder::getId);

        Page<Builder> page=new Page<>(pageNo,pageSize);
        builderService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addBuilder(@RequestBody Builder builder){
        builderService.save(builder);
        return Result.success("新增用户成功");
    }

    @PutMapping
    public Result<?> updateBuilder(@RequestBody Builder builder){
        builderService.updateById(builder);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<Builder> getBuilderById(@PathVariable("id") Integer id){
        Builder builder = builderService.getById(id);
        return Result.success(builder);
    }

    @DeleteMapping("/{id}")
    public Result<Builder> DeleteBuilderById(@PathVariable("id") Integer id){
        builderService.removeById(id);
        return Result.success("删除用户成功");
    }
}
