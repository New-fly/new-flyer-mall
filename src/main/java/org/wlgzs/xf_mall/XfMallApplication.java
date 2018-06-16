package org.wlgzs.xf_mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ServletComponentScan
@EnableJpaRepositories(basePackages={"org.wlgzs.xf_mall.dao"})
public class XfMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(XfMallApplication.class, args);
    }
}
