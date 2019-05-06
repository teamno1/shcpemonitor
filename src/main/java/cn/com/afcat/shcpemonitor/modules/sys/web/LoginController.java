package cn.com.afcat.shcpemonitor.modules.sys.web;

import cn.com.afcat.shcpemonitor.modules.sys.dao.TradeInfoMapper;
import cn.com.afcat.shcpemonitor.modules.sys.entity.TradeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Clock;

@Controller
public class LoginController {

    @Autowired
    TradeInfoMapper mapper;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        TradeInfo  tt=mapper.selectByPrimaryKey(1L);
        System.out.println(tt==null? "1":tt.getBrchName());
            return null;
    }
}
