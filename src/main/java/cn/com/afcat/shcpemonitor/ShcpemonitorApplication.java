package cn.com.afcat.shcpemonitor;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("cn.com.afcat.shcpemonitor.modules.sys.dao")
@EnableDubbo
public class ShcpemonitorApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(ShcpemonitorApplication.class, args);
	}

}
