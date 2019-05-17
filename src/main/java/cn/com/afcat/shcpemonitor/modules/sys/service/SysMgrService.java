package cn.com.afcat.shcpemonitor.modules.sys.service;

import cn.com.afcat.shcpemonitor.common.db.Page;
import cn.com.afcat.shcpemonitor.modules.sys.dao.SysMenuMapper;
import cn.com.afcat.shcpemonitor.modules.sys.dao.TradeInfoMapper;
import cn.com.afcat.shcpemonitor.modules.sys.entity.Menu;
import cn.com.afcat.shcpemonitor.modules.sys.entity.TradeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysMgrService {

    @Autowired
    TradeInfoMapper tradeDao;

    @Autowired
    SysMenuMapper menuMapper;

    public Page queryTradeInfo(Page page){
        List result=tradeDao.queryTradeList(page);
        page.setList(result);
        return page;
    }

    public void addTradeInfo(TradeInfo ti){
        tradeDao.insert(ti);
        int m=1/0;
    }

    /**
     * 获取菜单 不考虑用户角色
     * @return
     */
    public Map<Integer, List<Menu>> getAllFunctions(){
        List<Menu> functions=menuMapper.queryMenuList();
        Map<Integer, List<Menu>> functionMap = new HashMap<Integer, List<Menu>>();

        if (functions.size() > 0) {
            for (Menu function : functions) {
                if(function.getMenuType().equals("1")){
                    continue;
                }

                if (!functionMap.containsKey(Integer.parseInt(function.getMenuLevel()))) {
                    functionMap.put(Integer.parseInt(function.getMenuLevel()),
                            new ArrayList<Menu>());
                }
                functionMap.get(Integer.parseInt(function.getMenuLevel())).add(function);
            }

            // 菜单栏排序
            Collection<List<Menu>> c = functionMap.values();
            for (List<Menu> list : c) {
                Collections.sort(list, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu o1, Menu o2) {
                        return (int)(o1.getOrderNo()-o2.getOrderNo());
                    }
                });
            }

            return functionMap;
        }else{
            return null;
        }

    }

}
