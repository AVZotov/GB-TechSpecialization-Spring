package ru.gb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "param")
public class ApplicationConfiguration {
    private String CHARACTER_API;
}
