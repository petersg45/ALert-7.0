/**
 * blackduck-alert
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.integration.alert.common.descriptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackducksoftware.integration.alert.common.descriptor.config.DescriptorConfig;
import com.blackducksoftware.integration.alert.common.descriptor.config.DescriptorConfigType;
import com.blackducksoftware.integration.alert.common.exception.AlertException;

@Component
public class DescriptorMap {
    private final Map<String, Descriptor> descriptorMap;
    private final Map<String, ChannelDescriptor> channelDescriptorMap;
    private final Map<String, ProviderDescriptor> providerDescriptorMap;

    @Autowired
    public DescriptorMap(final List<ChannelDescriptor> channelDescriptors, final List<ProviderDescriptor> providerDescriptors) throws AlertException {
        descriptorMap = new HashMap<>(channelDescriptors.size() + providerDescriptors.size());
        channelDescriptorMap = initMap(channelDescriptors);
        providerDescriptorMap = initMap(providerDescriptors);
    }

    private <D extends Descriptor> Map<String, D> initMap(final List<D> descriptorList) throws AlertException {
        final Map<String, D> descriptorMapping = new HashMap<>(descriptorList.size());
        for (final D descriptor : descriptorList) {
            final String descriptorName = descriptor.getName();
            if (descriptorMap.containsKey(descriptorName)) {
                throw new AlertException("Found duplicate descriptor name of: " + descriptorName);
            }
            descriptorMap.put(descriptorName, descriptor);
            descriptorMapping.put(descriptorName, descriptor);
        }
        return descriptorMapping;
    }

    public List<DescriptorConfig> getStartupDescriptorConfigs() {
//        for (final Descriptor descriptor : descriptorMap.values()) {
//            final Set<DescriptorConfig> descriptorConfigs = descriptor.getAllConfigs();
//        }
        descriptorMap.values()
            .stream()
            .filter(descriptor -> descriptor.getAllConfigs()
                    .stream()
                    .fl)
    }

    public List<DescriptorConfig> getDistributionDescriptorConfigs() {
        return getDescriptorConfigs(DescriptorConfigType.DISTRIBUTION_CONFIG);
    }

    public List<DescriptorConfig> getGlobalDescriptorConfigs() {
        return getDescriptorConfigs(DescriptorConfigType.GLOBAL_CONFIG);
    }

    public List<DescriptorConfig> getProviderDescriptorConfigs() {
        return getDescriptorConfigs(DescriptorConfigType.PROVIDER_CONFIG);
    }

    public List<DescriptorConfig> getDescriptorConfigs(final DescriptorConfigType configType) {
        return descriptorMap.values()
                .stream()
                .filter(descriptor -> descriptor.getConfig(configType) != null)
                .map(descriptor -> descriptor.getConfig(configType))
                .collect(Collectors.toList());
    }

    public Descriptor getDescriptor(final String name) {
        return descriptorMap.get(name);
    }

    public ChannelDescriptor getChannelDescriptor(final String name) {
        return channelDescriptorMap.get(name);
    }

    public ProviderDescriptor getProviderDescriptor(final String name) {
        return providerDescriptorMap.get(name);
    }

    public Map<String, Descriptor> getDescriptorMap() {
        return descriptorMap;
    }

    public Map<String, ChannelDescriptor> getChannelDescriptorMap() {
        return channelDescriptorMap;
    }

    public Map<String, ProviderDescriptor> getProviderDescriptorMap() {
        return providerDescriptorMap;
    }
}
