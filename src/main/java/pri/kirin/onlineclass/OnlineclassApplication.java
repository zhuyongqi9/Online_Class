package pri.kirin.onlineclass;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication()
@MapperScan("pri.kirin.onlineclass.mapper")
@EnableTransactionManagement
public class OnlineclassApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineclassApplication.class, args);
    }

}
