package xyz.wavit.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import xyz.wavit.global.property.JwtProperty;

@Configuration
@EnableConfigurationProperties({JwtProperty.class})
public class PropertyConfig {}
