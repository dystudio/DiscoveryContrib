package com.nepxion.discovery.contrib.example.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nepxion.discovery.contrib.plugin.rocketmq.processor.RocketMQContribProcessor;

@Service
public class RocketMQImpl {
    private static final Logger LOG = LoggerFactory.getLogger(RocketMQImpl.class);

    private static final String CONSUMER_GROUP = "ContribGroup";

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RocketMQContribProcessor rocketMQContribProcessor;

    public void produce() {
        String destination = rocketMQContribProcessor.getDestination();
        String message = "ContribMessage";

        rocketMQTemplate.convertAndSend(destination, message);

        LOG.info("::::: RocketMQ produced, destination={}, message={}", destination, message);
    }

    @Service
    @RocketMQMessageListener(topic = "queue1", consumerGroup = CONSUMER_GROUP)
    public static class Subscriber1 implements RocketMQListener<String> {
        @Override
        public void onMessage(String message) {
            LOG.info("::::: RocketMQ subscribed, destination=queue1, message={}", message);
        }
    }
}