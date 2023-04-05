package ksmart.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MybatisApplication {

    private static final Logger log = LoggerFactory.getLogger(MybatisApplication.class);

        public static void main(String[] args) {
            SpringApplication.run(MybatisApplication.class, args);
    }

}
