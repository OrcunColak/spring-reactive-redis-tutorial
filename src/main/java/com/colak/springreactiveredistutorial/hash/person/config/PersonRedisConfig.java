package com.colak.springreactiveredistutorial.hash.person.config;

import com.colak.springreactiveredistutorial.hash.person.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class PersonRedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, Person> personRedisTemplate(
            ReactiveRedisConnectionFactory redisConnectionFactory,
            ObjectMapper objectMapper
    ) {
        // key serializer is string
        RedisSerializationContext.RedisSerializationContextBuilder<String, Person> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        builder.hashKey(new GenericToStringSerializer<>(Long.class))
                .hashValue(new Jackson2JsonRedisSerializer<>(objectMapper, Person.class));

        return new ReactiveRedisTemplate<>(redisConnectionFactory, builder.build());
    }
}
