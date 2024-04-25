package com.example.localguidebe.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AppConfiguration {

    @Bean
    public Cloudinary getCloudinary(){
        return new Cloudinary(
                ObjectUtils.asMap(
                "cloud_name", "dh06dk7vs",
                "api_key", "861591919956872",
                "api_secret", "iB44CBRCuvXp49vjdA6YjjbZ0rM",
                "secure", true
        ));
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
