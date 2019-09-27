package cn.com.afcat.shcpemonitor.modules.settlement.service;

import cn.com.afcat.shcpemonitor.modules.settlement.api.SettleMentOnlineService;
import com.alibaba.dubbo.config.annotation.Service;

@Service
public class SettleMentOnlineServiceImpl implements SettleMentOnlineService{
    @Override
    public String sayHello(String name) {
        return "hello "+name;
    }

    @Override
    public Double calc(double a, double b) {
        return a+b;
    }
}
