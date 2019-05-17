package cn.com.afcat.shcpemonitor.modules.sys.web;

import cn.com.afcat.shcpemonitor.common.json.AjaxJson;
import cn.com.afcat.shcpemonitor.modules.sys.entity.Menu;
import cn.com.afcat.shcpemonitor.modules.sys.entity.TradeInfo;
import cn.com.afcat.shcpemonitor.modules.sys.service.SysMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/loginController")
public class LoginController {
    @Autowired
    SysMgrService service;
    @RequestMapping("/")
    public String index(Model model){
        return "system/admin/login";
    }

    @RequestMapping("/login")
    public String login(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
        Map<Integer, List<Menu>> maps= service.getAllFunctions();
        List<Menu> menuList=new ArrayList<Menu>();
        if (maps !=null){
            menuList=maps.get(0);
        }
        TradeInfo user=new TradeInfo();
        user.setBrchNo("2121");
        user.setBrchName("sasafd");
        modelMap.put("menuList", menuList);
        modelMap.put("user", user);
        modelMap.put("currentDate", new Date());
        return "system/admin/index";
    }

    @RequestMapping("/getSubMenu")
    @ResponseBody
    public AjaxJson getSubMenu(HttpServletRequest request,
                               HttpServletResponse response) {

        AjaxJson retJson = new AjaxJson();



        String menuCode = request.getParameter("menuCode");

        //生成父菜单下的json字符串

        retJson.setObj("ssss");

        return retJson;

    }

    @RequestMapping( "/tab")
    public String tab() {
        return "system/admin/tab";
    }

    @RequestMapping( "/welcome")
    public String welcome() {
        return "system/admin/main";
    }
}
