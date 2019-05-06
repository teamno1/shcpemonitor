package cn.com.afcat.shcpemonitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.com.afcat.shcpemonitor.modules.sys.dao")
public class ShcpemonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShcpemonitorApplication.class, args);
	}

}
