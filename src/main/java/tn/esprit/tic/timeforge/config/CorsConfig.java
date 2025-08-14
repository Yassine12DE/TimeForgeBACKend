package tn.esprit.tic.timeforge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // ✅ Définir les origines autorisées
        config.setAllowedOrigins(List.of("http://localhost:4200", "http://example.com"));

        // ✅ Permettre les identifiants (cookies, sessions)
        config.setAllowCredentials(true);

        // ✅ Autoriser toutes les méthodes HTTP
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ Autoriser tous les headers
        config.setAllowedHeaders(List.of("*"));

        // ✅ Exposer certains headers aux clients (si nécessaire)
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
