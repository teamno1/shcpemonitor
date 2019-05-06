package cn.com.afcat.shcpemonitor.modules.sys.dao;

import cn.com.afcat.shcpemonitor.modules.sys.entity.SysParam;

public interface SysParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysParam record);

    int insertSelective(SysParam record);

    SysParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysParam record);

    int updateByPrimaryKey(SysParam record);
}