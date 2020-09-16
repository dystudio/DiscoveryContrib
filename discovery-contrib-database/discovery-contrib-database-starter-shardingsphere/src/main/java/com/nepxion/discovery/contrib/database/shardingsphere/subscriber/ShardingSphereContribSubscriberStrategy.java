package com.nepxion.discovery.contrib.database.shardingsphere.subscriber;

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
import com.nepxion.discovery.contrib.database.shardingsphere.constant.ShardingSphereContribConstant;

public class ShardingSphereContribSubscriberStrategy implements ContribSubscriberStrategy {
    @Override
    public void apply(String key, String value) {
        if (!StringUtils.equals(key, ShardingSphereContribConstant.SHARDING_SPHERE)) {
            return;
        }

        // 实现灰度发布切换逻辑
    }
}