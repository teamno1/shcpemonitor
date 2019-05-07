package cn.com.afcat.shcpemonitor.modules.sys.web;

import cn.com.afcat.shcpemonitor.modules.sys.dao.TradeInfoMapper;
import cn.com.afcat.shcpemonitor.modules.sys.entity.TradeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Clock;

@Controller
@RequestMapping("/al")
public class LoginController {
    @RequestMapping("/a")
    public String index(Model model){
        System.out.println("=================");
        model.addAttribute("11");
        return "html";
       // return "login2";
    }
}
