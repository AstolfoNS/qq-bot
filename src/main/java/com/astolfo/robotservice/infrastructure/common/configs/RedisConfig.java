package com.astolfo.robotservice.infrastructure.common.configs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.jackson2.SimpleGrantedAuthorityMixin;

@EnableCaching
@Configuration
public class RedisConfig {

    @Value("#{redisProperties.redisHost}")
    private String redisHost;

    @Value("#{redisProperties.redisPort}")
    private Integer redisPort;

    @Value("#{redisProperties.redisPassword}")
    private String redisPassword;

    @Value("#{redisProperties.redissonAddress}")
    private String redissonAddress;

    @Value("#{redisProperties.redissonDatabase}")
    private Integer redissonDatabase;


    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        config
                .useSingleServer()
                .setAddress(redissonAddress)
                .setDatabase(redissonDatabase)
                .setPassword(redisPassword);

        return Redisson.create(config);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setPassword(redisPassword);

        return new LettuceConnectionFactory(config);
    }

    private static ObjectMapper customizeRedisObjectMapper(ObjectMapper objectMapper) {
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        objectMapper.addMixIn(GrantedAuthority.class, SimpleGrantedAuthorityMixin.class);

        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return objectMapper;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer(customizeRedisObjectMapper(objectMapper.copy()));

        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        template.setConnectionFactory(factory);

        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jsonSerializer);

        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();

        return template;
    }

}
