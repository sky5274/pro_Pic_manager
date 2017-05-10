package com.dao.impl;

import java.util.List;

import com.dao.entity.ClassInfo;

public interface ClassInfoMapper {
    int insert(ClassInfo record);

    int insertSelective(ClassInfo record);
    
   List<ClassInfo> selectInfo();
}