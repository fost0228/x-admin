package com.lantu.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lantu.common.utils.JwtUtil;
import com.lantu.sys.entity.User;
import com.lantu.sys.mapper.UserMapper;
import com.lantu.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @author forrest
 * @since 2023-04-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> login(User user) {
        //search by username and password
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);
        //if result is not null, and the password matches, then generate a token and save user info into redis
        if(loginUser != null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())){
            //uuid
//            String key = "user:" + UUID.randomUUID();

            //save in redis
            loginUser.setPassword(null);
            String token = jwtUtil.createToken(loginUser);
//            redisTemplate.opsForValue().set(token, loginUser, 30, TimeUnit.MINUTES);

            //return value
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            return data;
        }
        return null;
    }

//    @Override
//    public Map<String, Object> login(User user) {
//        //search by username and password
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername, user.getUsername());
//        wrapper.eq(User::getPassword,user.getPassword());
//        User loginUser = this.baseMapper.selectOne(wrapper);
//        //if result is not null, then generate a token and save user info into redis
//        if(loginUser != null){
//            //uuid
//            String key = "user:" + UUID.randomUUID();
//            //save in redis
//            loginUser.setPassword(null);
//            redisTemplate.opsForValue().set(key, loginUser, 30, TimeUnit.MINUTES);
//            //return value
//            Map<String, Object> data = new HashMap<>();
//            data.put("token", key);
//            return data;
//        }
//        return null;
//    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        //get user info from redis by token
        Object obj = redisTemplate.opsForValue().get(token);
        if(obj != null){
            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name", loginUser.getUsername());
            data.put("avatar", loginUser.getAvatar());

            //put role into data
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles", roleList);

            return data;
        }

        return null;
    }

    @Override
    public void logOut(String token) {
        redisTemplate.delete(token);
    }
}
