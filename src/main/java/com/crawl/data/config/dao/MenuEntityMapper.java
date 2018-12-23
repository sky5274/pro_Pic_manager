package com.crawl.data.config.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.crawl.data.config.dao.entity.MenuEntity;

@Mapper
public interface MenuEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuEntity record);

    int insertSelective(MenuEntity record);

    MenuEntity selectByPrimaryKey(Integer id);
    List<MenuEntity> queryAll();

    int updateByPrimaryKeySelective(MenuEntity record);

    int updateByPrimaryKey(MenuEntity record);
}