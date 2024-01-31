package com.example.localguidebe.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
