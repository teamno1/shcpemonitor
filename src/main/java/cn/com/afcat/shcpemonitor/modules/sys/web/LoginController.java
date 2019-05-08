package cn.com.afcat.shcpemonitor.modules.sys.web;

import cn.com.afcat.shcpemonitor.common.persistence.Page;
import cn.com.afcat.shcpemonitor.modules.sys.entity.TradeInfo;
import cn.com.afcat.shcpemonitor.modules.sys.service.SysMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @Autowired
    SysMgrService service;
    @RequestMapping("/")
    public String index(Model model){
        System.out.println("=================");
        return "login";
    }

    @RequestMapping("/login")
    public String login(TradeInfo user,Model model){
        System.out.println(user.getBrchNo());
        //user =service.selectByPrimaryKey(1L);
        Page page=new Page();
        page=service.queryTradeInfo(page);
        System.out.println(page.getList().size());
        model.addAttribute("msg",user);
        return "home";
    }
}
