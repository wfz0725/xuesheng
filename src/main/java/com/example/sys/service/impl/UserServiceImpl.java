package com.example.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.sys.entity.User;
import com.example.sys.mapper.UserMapper;
import com.example.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-12-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Map<String, Object> login(User user) {
        //根据密码查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);
        //结果不为空并且密码和传入密码匹配，则生成token,并将用户信息存入redis
        if (loginUser!=null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())){
            //暂时用UUID，终极jwt
            String key="user:"+UUID.randomUUID();

            //存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);

            //f返回数据
            Map<String, Object> data=new HashMap<>();
            data.put("token",key);
            return  data;

        }
        return null;

    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        //根据token获取用户信息，redis
        Object obj = redisTemplate.opsForValue().get(token);
        if (obj!=null){
            User loginUser= JSON.parseObject(JSON.toJSONString(obj),User.class);
            Map<String,Object> data=new HashMap<>();
            data.put("name",loginUser.getUsername());
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }
}
