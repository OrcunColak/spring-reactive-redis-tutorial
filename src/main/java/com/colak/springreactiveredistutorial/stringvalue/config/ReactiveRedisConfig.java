package com.colak.springreactiveredistutorial.stringvalue.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class ReactiveRedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    // See https://medium.com/@raphael3213/multiple-redis-connections-in-spring-boot-37f632e8e64f
    // how to create multiple redis connections
    // @Bean(name = "redisConnectionFactoryFirst")
    public LettuceConnectionFactory redisConnectionFactoryFirst() {
        // Same as
        // return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisHost, redisPort);


        lettuceConnectionFactory.setDatabase(14);
        return lettuceConnectionFactory;
    }

    //@Bean(name = "redisConnectionFactorySecond")
    public LettuceConnectionFactory redisConnectionFactorySecond() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisHost, redisPort);
        lettuceConnectionFactory.setDatabase(15);
        return lettuceConnectionFactory;
    }

    @Bean
    public ReactiveValueOperations<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory redisConnectionFactoryFirst) {
        // key serializer is string
        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        // Value serializer is json
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        builder.value(genericJackson2JsonRedisSerializer);

        ReactiveRedisTemplate<String, Object> reactiveRedisTemplate = new ReactiveRedisTemplate<>(redisConnectionFactoryFirst, builder.build());
        return reactiveRedisTemplate.opsForValue();
    }


}
