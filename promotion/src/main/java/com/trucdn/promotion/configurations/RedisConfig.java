package com.trucdn.promotion.configurations;
import com.trucdn.promotion.common.constant.RedisKeyConstant;
import com.trucdn.promotion.subscriber.UserNotifySubscriber;
import com.trucdn.promotion.subscriber.UserQualifiedSubscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;


    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
    }

    @Bean
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    UserNotifySubscriber userNotifySubscriber() {
        return new UserNotifySubscriber();
    }

    @Bean
    MessageListenerAdapter userNotifyMessageListener() {
        return new MessageListenerAdapter(userNotifySubscriber());
    }

    @Bean
    RedisMessageListenerContainer userNotifyRedisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory( redisConnectionFactory() );
        container.addMessageListener( userNotifyMessageListener(), userNotifyTopic() );
        return container;
    }

    @Bean
    UserQualifiedSubscriber userQualifiedSubscriber() {
        return new UserQualifiedSubscriber();
    }

    @Bean
    MessageListenerAdapter userQualifiedMessageListener() {
        return new MessageListenerAdapter(userQualifiedSubscriber());
    }

    @Bean
    RedisMessageListenerContainer userQualifiedRedisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory( redisConnectionFactory() );
        container.addMessageListener( userQualifiedMessageListener(), userQualifiedTopic() );
        return container;
    }

    @Bean
    ChannelTopic userNotifyTopic() {
        return new ChannelTopic(RedisKeyConstant.USER_LOGIN_TOPIC_KEY );
    }

    @Bean
    ChannelTopic userQualifiedTopic() {
        return new ChannelTopic(RedisKeyConstant.PROMOTION_USER_QUALIFIED_TOPIC_KEY );
    }
}