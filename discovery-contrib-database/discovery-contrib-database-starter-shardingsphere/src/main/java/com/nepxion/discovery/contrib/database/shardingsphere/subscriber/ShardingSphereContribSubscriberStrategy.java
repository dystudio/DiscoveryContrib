package com.nepxion.discovery.contrib.database.shardingsphere.subscriber;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import com.nepxion.discovery.common.entity.ParameterServiceEntity;
import com.nepxion.discovery.contrib.common.subscriber.ContribSubscriberStrategy;
import com.nepxion.discovery.contrib.database.shardingsphere.constant.ShardingSphereContribConstant;

public class ShardingSphereContribSubscriberStrategy implements ContribSubscriberStrategy {
    @Override
    public void apply(List<ParameterServiceEntity> parameterServiceEntityList) {
        for (ParameterServiceEntity parameterServiceEntity : parameterServiceEntityList) {
            parameterServiceEntity.getParameterMap().get(ShardingSphereContribConstant.SHARDING_SPHERE)
        }
    }
}