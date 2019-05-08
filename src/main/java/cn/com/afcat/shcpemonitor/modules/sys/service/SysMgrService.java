package cn.com.afcat.shcpemonitor.modules.sys.service;

import cn.com.afcat.shcpemonitor.common.persistence.Page;
import cn.com.afcat.shcpemonitor.modules.sys.dao.TradeInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMgrService {

    @Autowired
    TradeInfoMapper tradeDao;

    public Page queryTradeInfo(Page page){
        List result=tradeDao.queryTradeList(page);
        page.setList(result);
        return page;
    }
}
