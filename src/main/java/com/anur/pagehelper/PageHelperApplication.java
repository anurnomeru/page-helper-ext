package com.anur.pagehelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = PageHelperAutoConfiguration.class)
public class PageHelperApplication {

    public static void main(String[] args) {

        SpringApplication.run(PageHelperApplication.class, args);
    }
}
