package com.lquirin.appRencontre;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApplicationRencontreJeux {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRencontreJeux.class, args);
    }



}
