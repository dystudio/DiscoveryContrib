package com.nepxion.discovery.contrib.common.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.nepxion.discovery.plugin.strategy.adapter.DefaultDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryMatcherStrategy;

public class DefaultContribEnabledAdapter implements ContribEnabledAdapter {
    @Autowired(required = false)
    protected List<ContribEnabledStrategy> contribEnabledStrategyList;

    @Autowired
    protected DiscoveryMatcherStrategy discoveryMatcherStrategy;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected PluginContextHolder pluginContextHolder;

    @Autowired
    protected DefaultDiscoveryEnabledAdapter discoveryEnabledAdapter;

    @Override
    public boolean apply() {
        boolean enabled = applyRegion();
        if (!enabled) {
            return false;
        }

        enabled = applyVersion();
        if (!enabled) {
            return false;
        }

        enabled = applyAddress();
        if (!enabled) {
            return false;
        }

        return applyStrategy();
    }

    public boolean applyRegion() {
        String serviceId = pluginAdapter.getServiceId();

        String regions = discoveryEnabledAdapter.getRegions(serviceId);
        if (StringUtils.isEmpty(regions)) {
            return true;
        }

        String region = pluginAdapter.getRegion();

        // 如果精确匹配不满足，尝试用通配符匹配
        List<String> regionList = StringUtil.splitToList(regions, DiscoveryConstant.SEPARATE);
        if (regionList.contains(region)) {
            return true;
        }

        // 通配符匹配。前者是通配表达式，后者是具体值
        for (String regionPattern : regionList) {
            if (discoveryMatcherStrategy.match(regionPattern, region)) {
                return true;
            }
        }

        return false;
    }

    public boolean applyVersion() {
        String serviceId = pluginAdapter.getServiceId();

        String versions = discoveryEnabledAdapter.getVersions(serviceId);
        if (StringUtils.isEmpty(versions)) {
            return true;
        }

        String version = pluginAdapter.getVersion();

        // 如果精确匹配不满足，尝试用通配符匹配
        List<String> versionList = StringUtil.splitToList(versions, DiscoveryConstant.SEPARATE);
        if (versionList.contains(version)) {
            return true;
        }

        // 通配符匹配。前者是通配表达式，后者是具体值
        for (String versionPattern : versionList) {
            if (discoveryMatcherStrategy.match(versionPattern, version)) {
                return true;
            }
        }

        return false;
    }

    public boolean applyAddress() {
        String serviceId = pluginAdapter.getServiceId();

        String addresses = discoveryEnabledAdapter.getAddresses(serviceId);
        if (StringUtils.isEmpty(addresses)) {
            return true;
        }

        // 如果精确匹配不满足，尝试用通配符匹配
        List<String> addressList = StringUtil.splitToList(addresses, DiscoveryConstant.SEPARATE);
        if (addressList.contains(pluginAdapter.getHost() + ":" + pluginAdapter.getPort()) || addressList.contains(pluginAdapter.getHost()) || addressList.contains(String.valueOf(pluginAdapter.getPort()))) {
            return true;
        }

        // 通配符匹配。前者是通配表达式，后者是具体值
        for (String addressPattern : addressList) {
            if (discoveryMatcherStrategy.match(addressPattern, pluginAdapter.getHost() + ":" + pluginAdapter.getPort()) || discoveryMatcherStrategy.match(addressPattern, pluginAdapter.getHost()) || discoveryMatcherStrategy.match(addressPattern, String.valueOf(pluginAdapter.getPort()))) {
                return true;
            }
        }

        return false;
    }

    public boolean applyStrategy() {
        if (CollectionUtils.isEmpty(contribEnabledStrategyList)) {
            return true;
        }

        for (ContribEnabledStrategy contribEnabledStrategy : contribEnabledStrategyList) {
            boolean enabled = contribEnabledStrategy.apply();
            if (!enabled) {
                return false;
            }
        }

        return true;
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public PluginContextHolder getPluginContextHolder() {
        return pluginContextHolder;
    }
}