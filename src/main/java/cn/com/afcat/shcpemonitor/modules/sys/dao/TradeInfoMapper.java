package cn.com.afcat.shcpemonitor.modules.sys.dao;

import cn.com.afcat.shcpemonitor.common.db.Page;
import cn.com.afcat.shcpemonitor.modules.sys.entity.TradeInfo;

import java.util.List;

public interface TradeInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradeInfo record);

    int insertSelective(TradeInfo record);

    TradeInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeInfo record);

    int updateByPrimaryKey(TradeInfo record);

    List<TradeInfo> queryTradeList(Page page);




}