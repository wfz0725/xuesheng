package com.example.sys.service.impl;

import com.example.sys.entity.Health;
import com.example.sys.mapper.HealthMapper;
import com.example.sys.service.IHealthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-12-12
 */
@Service
public class HealthServiceImpl extends ServiceImpl<HealthMapper, Health> implements IHealthService {

}
