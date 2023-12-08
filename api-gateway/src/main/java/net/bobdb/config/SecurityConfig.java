package net.bobdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(c->c.disable())
            .authorizeExchange(e->e
                .pathMatchers("/eureka/**")
                .permitAll()
                .anyExchange()
                .authenticated())
            .oauth2ResourceServer((resourceServer)->resourceServer.jwt(Customizer.withDefaults()));
        return http.build();
    }

}



