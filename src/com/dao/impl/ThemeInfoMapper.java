package com.dao.impl;

import java.util.List;

import com.dao.entity.ThemeInfo;

public interface ThemeInfoMapper {
    int insert(ThemeInfo record);

    int insertSelective(ThemeInfo record);
    
    List<ThemeInfo> selectInfo();
}