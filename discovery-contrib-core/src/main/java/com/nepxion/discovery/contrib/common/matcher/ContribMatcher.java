package com.nepxion.discovery.contrib.common.matcher;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryMatcherStrategy;

public class ContribMatcher {
    @Autowired
    protected DiscoveryMatcherStrategy discoveryMatcherStrategy;

    @Autowired
    protected PluginAdapter pluginAdapter;

    public boolean match(String targetValues, String value) {
        // 如果精确匹配不满足，尝试用通配符匹配
        List<String> targetValueList = StringUtil.splitToList(targetValues, DiscoveryConstant.SEPARATE);
        if (targetValueList.contains(value)) {
            return true;
        }

        // 通配符匹配。前者是通配表达式，后者是具体值
        for (String targetValuePattern : targetValueList) {
            if (discoveryMatcherStrategy.match(targetValuePattern, value)) {
                return true;
            }
        }

        return false;
    }

    public boolean matchAddress(String addresses) {
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
}