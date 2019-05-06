package cn.com.afcat.shcpemonitor.modules.sys.dao;

import cn.com.afcat.shcpemonitor.modules.sys.entity.TradeInfo;

public interface TradeInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradeInfo record);

    int insertSelective(TradeInfo record);

    TradeInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeInfo record);

    int updateByPrimaryKey(TradeInfo record);
}