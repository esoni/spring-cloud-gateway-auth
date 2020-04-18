package it.italiancoders.pizzashop.gateway;

import it.italiancoders.pizzashop.gateway.authentication.SecurityProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@EnableConfigurationProperties(SecurityProperty.class)
@SpringBootApplication
public class GatewayApplication {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setEnableTransactionSupport(true);
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
