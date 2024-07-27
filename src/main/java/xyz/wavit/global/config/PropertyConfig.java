package xyz.wavit.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import xyz.wavit.global.property.FcmProperty;
import xyz.wavit.global.property.JwtProperty;
import xyz.wavit.global.property.S3Property;

@Configuration
@EnableConfigurationProperties({JwtProperty.class, S3Property.class, FcmProperty.class})
public class PropertyConfig {}
