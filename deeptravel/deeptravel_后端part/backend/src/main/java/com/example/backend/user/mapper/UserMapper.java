package com.example.backend.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author abc
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2025-03-31 20:16:56
* @Entity com.example.user.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




