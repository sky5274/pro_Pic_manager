package com.crawl.config.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crawl.data.config.dao.entity.MenuEntity;

@Service
public interface MenuService {
	
	public List<MenuEntity> getMenuList();
	public Boolean saveMenu(MenuEntity menu);
	public Boolean updateMenu(MenuEntity menu);
	public Boolean delMneu(MenuEntity menu);
}
