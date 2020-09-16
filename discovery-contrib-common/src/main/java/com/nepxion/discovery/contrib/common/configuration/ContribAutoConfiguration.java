package com.nepxion.discovery.contrib.common.configuration;

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

import com.nepxion.discovery.contrib.common.cache.ContribCache;
import com.nepxion.discovery.contrib.common.subscriber.ContribMatcher;
import com.nepxion.discovery.contrib.common.subscriber.ContribSubscriber;

@Configuration
public class ContribAutoConfiguration {
    @Bean
    public ContribCache contribCache() {
        return new ContribCache();
    }

    @Bean
    @ConditionalOnMissingBean
    public ContribSubscriber contribSubscriber() {
        return new ContribSubscriber();
    }

    @Bean
    @ConditionalOnMissingBean
    public ContribMatcher contribMatcher() {
        return new ContribMatcher();
    }
}