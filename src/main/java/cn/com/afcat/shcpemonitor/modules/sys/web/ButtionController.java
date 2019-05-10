package cn.com.afcat.shcpemonitor.modules.sys.web;

import cn.com.afcat.shcpemonitor.modules.sys.dao.TradeInfoMapper;
import cn.com.afcat.shcpemonitor.modules.sys.entity.TradeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/botton")
public class ButtionController {
    @Autowired
    TradeInfoMapper mapper;
    @RequestMapping("/botton")
    public String index(Model model){
        System.out.println("=================");
        return "1-1P22G11331/demo1";
    }

}
