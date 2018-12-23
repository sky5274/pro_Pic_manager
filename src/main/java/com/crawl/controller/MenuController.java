package com.crawl.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crawl.config.service.MenuService;
import com.crawl.data.config.dao.entity.MenuEntity;
import com.crawl.pub.Result;
import com.crawl.pub.ResultCode;
import com.crawl.pub.ResultUtil;

/**
 * 菜单 控制器
 * @author 王帆
 * @date  2018年12月23日 下午5:30:52
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
	@Resource
	private MenuService menuSerive;
	
	@RequestMapping("list")
	public Result<List<MenuEntity>> getMenuList(){
		return ResultUtil.getOk(ResultCode.OK, menuSerive.getMenuList());
	}
	@RequestMapping("add")
	public Result<Boolean> addMneu(MenuEntity menu){
		return ResultUtil.getOk(ResultCode.OK, menuSerive.saveMenu(menu));
	}
	@RequestMapping("update")
	public Result<Boolean> updateMenu(MenuEntity menu){
		return ResultUtil.getOk(ResultCode.OK, menuSerive.updateMenu(menu));
	}
	@RequestMapping("del")
	public Result<Boolean> delMenu(MenuEntity menu){
		return ResultUtil.getOk(ResultCode.OK, menuSerive.delMneu(menu));
	}
	
}
