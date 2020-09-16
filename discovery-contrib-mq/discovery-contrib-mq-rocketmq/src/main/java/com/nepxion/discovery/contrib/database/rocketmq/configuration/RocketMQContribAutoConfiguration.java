package com.nepxion.discovery.contrib.database.rocketmq.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.contrib.database.rocketmq.subscriber.RocketMQContribSubscriberStrategy;

@Configuration
public class RocketMQContribAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RocketMQContribSubscriberStrategy rocketMQContribSubscriberStrategy() {
        return new RocketMQContribSubscriberStrategy();
    }
}