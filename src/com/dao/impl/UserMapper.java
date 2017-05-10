package com.dao.impl;

import java.util.List;

import com.dao.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);
    
    List<User> selectALL();
    
    List<User> selectUserRangeByName(String username);

    List<User> selectVisitorRangeByName(String username);
    
    List<User> selectManagerRangeByName(String username);
    
    /**
     * 有选择插入不空值
     * */
    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByName(String username);
    
    User selectByUser(User u);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}