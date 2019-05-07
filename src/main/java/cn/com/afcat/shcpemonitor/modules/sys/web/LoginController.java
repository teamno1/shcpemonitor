package cn.com.afcat.shcpemonitor.modules.sys.web;

import cn.com.afcat.shcpemonitor.modules.sys.dao.TradeInfoMapper;
import cn.com.afcat.shcpemonitor.modules.sys.entity.TradeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @Autowired
    TradeInfoMapper mapper;
    @RequestMapping("/")
    public String index(Model model){
        System.out.println("=================");
        return "login";
    }

    @RequestMapping("/login")
    public String login(TradeInfo user,Model model){
        System.out.println(user.getBrchNo());
        user =mapper.selectByPrimaryKey(1L);
        model.addAttribute("msg",user);
        return "home";
    }
}
