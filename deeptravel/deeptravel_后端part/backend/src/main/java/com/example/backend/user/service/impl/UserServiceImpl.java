package com.example.backend.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.user.domain.User;
import com.example.user.service.UserService;
import com.example.backend.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author abc
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-03-31 20:16:56
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




