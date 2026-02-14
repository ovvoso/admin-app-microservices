package ms.msproduit.ms_produits.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "redis")
@Configuration
public class RedisConfig {

}

