package com.dao.impl;

import java.util.List;
import java.util.Map;

import com.dao.entity.PageInfo;
import com.dao.entity.Picture;

public interface PictureMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Picture record);

    int insertSelective(Picture record);

    Picture selectByPrimaryKey(Integer id);
    
    List<Picture> selectTheme(String theme);
    
    List<Picture> selectRangByTitle(String title);
    
    List<Picture> selectRangByTheme(String theme);

    List<Picture> selectRangToByClass(String clazz);
    
    List<Picture> selectRangToPageByTitle(PageInfo info);
    
    List<Picture> selectRangToPageByTheme(PageInfo info);
    
    List<Picture> selectRangToPageByClass(PageInfo info);

    int updateByPrimaryKeySelective(Picture record);

    int updateByPrimaryKey(Picture record);
    
    int updateTheme(Map<String, String> map);
    
    int updateClass(Map<String, String> map);
}