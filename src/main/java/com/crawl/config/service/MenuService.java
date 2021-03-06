package com.crawl.config.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crawl.data.config.dao.entity.MenuEntity;
import com.crawl.data.config.dao.entity.MenuNode;
import com.crawl.pub.BasePageRequest;
import com.crawl.pub.Page;

@Service
public interface MenuService {
	
	public List<MenuEntity> getMenuList();
	public Boolean saveMenu(MenuEntity menu);
	public Boolean updateMenu(MenuEntity menu);
	public Boolean delMneu(MenuEntity menu);
	/**
	 * 分页查询菜单
	 * @param page
	 * @return
	 * @author 王帆
	 * @date 2018年12月23日 下午10:16:38
	 */
	public Page<MenuEntity> getMenuPage(BasePageRequest page);
	
	/**
	 * 查询 菜单节点
	 * @return
	 * @author 王帆
	 * @date 2018年12月24日 下午3:09:56
	 */
	public List<MenuNode> getMenuNode();
}
