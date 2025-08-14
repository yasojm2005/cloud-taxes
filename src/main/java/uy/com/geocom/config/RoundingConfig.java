package uy.com.geocom.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.RoundingMode;

import uy.com.geocom.domain.shared.RoundingPolicy;

@Configuration
public class RoundingConfig {
  @Bean
  @ConfigurationProperties(prefix = "app.rounding")
  public RoundingPolicy roundingPolicy() { return new RoundingPolicy(2, RoundingMode.HALF_UP); }
}
