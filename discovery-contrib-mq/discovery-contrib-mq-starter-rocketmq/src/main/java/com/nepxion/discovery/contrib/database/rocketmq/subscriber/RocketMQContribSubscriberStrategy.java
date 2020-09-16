package com.nepxion.discovery.contrib.database.rocketmq.subscriber;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.contrib.common.subscriber.ContribSubscriberStrategy;
import com.nepxion.discovery.contrib.database.rocketmq.constant.RocketMQContribConstant;

public class RocketMQContribSubscriberStrategy implements ContribSubscriberStrategy {
    @Override
    public void apply(String key, String value) {
        if (!StringUtils.equals(key, RocketMQContribConstant.ROCKET_MQ)) {
            return;
        }

        System.out.println(key + "=" + value);

        // 实现灰度发布切换逻辑
    }
}