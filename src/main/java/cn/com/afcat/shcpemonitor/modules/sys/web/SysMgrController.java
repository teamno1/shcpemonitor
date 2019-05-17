package cn.com.afcat.shcpemonitor.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统管理类
 * @author litao
 */
@Controller
@RequestMapping("/sys")
public class SysMgrController {

    @RequestMapping("/getSysStatus")
    public String getSysStatus(){
        System.out.println("=============system===");
        return "system/sysStatus";
    }

    /**
     * 显示交易列表
     * @return
     */
    @RequestMapping("/listTrade")
    public String listTrade(){




        return "views/demo1";
    }

}
