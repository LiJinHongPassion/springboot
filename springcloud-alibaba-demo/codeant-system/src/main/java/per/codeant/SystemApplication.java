package per.codeant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@MapperScan({"per.codeant.**.proxy"})
@SpringBootApplication
public class SystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(per.codeant.SystemApplication.class,args);
	}
}
