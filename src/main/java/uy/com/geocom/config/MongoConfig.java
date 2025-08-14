package uy.com.geocom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
  @Bean
  MongoCustomConversions customConversions() { return MongoCustomConversions.create(configurer -> {}); }
}
