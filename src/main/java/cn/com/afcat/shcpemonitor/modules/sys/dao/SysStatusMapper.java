package cn.com.afcat.shcpemonitor.modules.sys.dao;

import cn.com.afcat.shcpemonitor.modules.sys.entity.SysStatus;

public interface SysStatusMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysStatus record);

    int insertSelective(SysStatus record);

    SysStatus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysStatus record);

    int updateByPrimaryKey(SysStatus record);
}