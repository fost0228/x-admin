package com.lantu.sys.service.impl;

import com.lantu.sys.entity.User;
import com.lantu.sys.mapper.UserMapper;
import com.lantu.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
