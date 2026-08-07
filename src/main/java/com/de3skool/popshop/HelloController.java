package com.de3skool.popshop;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador simple tipo "Hello World". A proposito se mantiene sin
 * base de datos ni logica de negocio: esta app existe principalmente
 * para ejercitar el pipeline de GitHub Actions (build, tests, calidad,
 * seguridad y publicacion), no para resolver un caso de negocio real.
 */
@RestController
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Value("${spring.application.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/")
    public Map<String, Object> root() {
        log.info("GET / invocado");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("app", appName);
        body.put("version", appVersion);
        body.put("message", "Hola Mundo desde PopShop");
        body.put("endpoints", List.of(
                "/",
                "/api/hello",
                "/actuator/health",
                "/actuator/info",
                "/actuator/metrics"
        ));
        return body;
    }

    @GetMapping("/api/hello")
    public Map<String, Object> hello() {
        log.info("GET /api/hello invocado - app={} version={}", appName, appVersion);
        return Map.of(
                "app", appName,
                "version", appVersion,
                "message", "Hola Mundo desde PopShop"
        );
    }
}
