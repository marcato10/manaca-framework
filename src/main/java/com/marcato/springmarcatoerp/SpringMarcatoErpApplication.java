package com.marcato.springmarcatoerp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringMarcatoErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMarcatoErpApplication.class, args);
    }

}
