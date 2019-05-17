package cn.com.afcat.shcpemonitor.modules.sys.dao;

import cn.com.afcat.shcpemonitor.modules.sys.entity.Menu;

import java.util.List;

public interface SysMenuMapper {

    List<Menu> queryMenuList();
}