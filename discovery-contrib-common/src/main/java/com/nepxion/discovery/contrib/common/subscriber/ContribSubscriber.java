package com.nepxion.discovery.contrib.common.subscriber;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.common.entity.ParameterEntity;
import com.nepxion.discovery.common.entity.ParameterServiceEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.event.ParameterChangedEvent;
import com.nepxion.eventbus.annotation.EventBus;

@EventBus
public class ContribSubscriber {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired(required = false)
    protected List<ContribSubscriberStrategy> contribSubscriberStrategyList;

    @Subscribe
    public void onParameterChanged(ParameterChangedEvent parameterChangedEvent) {
        ParameterEntity parameterEntity = parameterChangedEvent.getParameterEntity();
        if (parameterEntity == null) {
            return;
        }

        Map<String, List<ParameterServiceEntity>> parameterServiceMap = parameterEntity.getParameterServiceMap();

        String serviceId = pluginAdapter.getServiceId();
        List<ParameterServiceEntity> parameterServiceEntityList = parameterServiceMap.get(serviceId);

        applyStrategy(parameterServiceEntityList);
    }

    public void applyStrategy(List<ParameterServiceEntity> parameterServiceEntityList) {
        if (CollectionUtils.isEmpty(contribSubscriberStrategyList)) {
            return;
        }

        for (ContribSubscriberStrategy contribSubscriberStrategy : contribSubscriberStrategyList) {
            contribSubscriberStrategy.apply(parameterServiceEntityList);
        }
    }
}