package com.de3skool.popshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class PopshopApplication {

    private static final Logger log = LoggerFactory.getLogger(PopshopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PopshopApplication.class, args);
    }

    /**
     * Deja constancia en el log de que la app quedo arriba y con que perfil/puerto.
     * Este tipo de mensaje es el que luego se puede "grepear" desde un job de
     * GitHub Actions para confirmar que el arranque fue exitoso.
     */
    @Bean
    ApplicationRunner logStartup(Environment env) {
        return args -> {
            String[] profiles = env.getActiveProfiles();
            String activeProfiles = profiles.length > 0 ? String.join(",", profiles) : "default";
            log.info(
                    "PopShop lista para recibir peticiones. puerto={} perfiles={}",
                    env.getProperty("server.port", "8080"),
                    activeProfiles
            );
        };
    }
}
