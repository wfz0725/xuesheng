package com.example.sys.service.impl;

import com.example.sys.entity.Data;
import com.example.sys.mapper.DataMapper;
import com.example.sys.service.IDataService;
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
public class DataServiceImpl extends ServiceImpl<DataMapper, Data> implements IDataService {

}
