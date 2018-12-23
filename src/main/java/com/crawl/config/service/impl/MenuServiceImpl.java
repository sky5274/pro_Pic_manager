package com.crawl.config.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.crawl.config.service.MenuService;
import com.crawl.data.config.dao.MenuEntityMapper;
import com.crawl.data.config.dao.entity.MenuEntity;

@Service
public class MenuServiceImpl implements MenuService{
	@Resource
	private MenuEntityMapper menuMapper;
	
	@Override
	public List<MenuEntity> getMenuList() {
		return menuMapper.queryAll();
	}

	@Override
	public Boolean saveMenu(MenuEntity menu) {
		if(menu.getPid()==null) {
			menu.setPid(0);
		}
		return menuMapper.insertSelective(menu)>0;
	}

	@Override
	public Boolean updateMenu(MenuEntity menu) {
		return menuMapper.updateByPrimaryKeySelective(menu)>0;
	}

	@Override
	public Boolean delMneu(MenuEntity menu) {
		return menuMapper.deleteByPrimaryKey(menu.getId())>0;
	}

}
