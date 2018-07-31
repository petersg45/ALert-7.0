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
package com.blackducksoftware.integration.alert.channel;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackducksoftware.integration.alert.channel.event.ChannelEvent;
import com.blackducksoftware.integration.alert.common.ContentConverter;
import com.blackducksoftware.integration.alert.common.digest.model.DigestModel;
import com.blackducksoftware.integration.alert.common.digest.model.ProjectData;
import com.blackducksoftware.integration.alert.common.enumeration.DigestType;

@Transactional
@Component
public class DistributionChannelManager {
    private final ContentConverter contentConverter;

    @Autowired
    public DistributionChannelManager(final ContentConverter contentConverter) {
        this.contentConverter = contentConverter;
    }

    public DigestModel getTestMessageModel() {
        final Collection<ProjectData> projectDataCollection = Arrays.asList(new ProjectData(DigestType.REAL_TIME, "Hub Alert", "Test Message", Collections.emptyList(), Collections.emptyMap()));
        final DigestModel digestModel = new DigestModel(projectDataCollection);
        return digestModel;
    }

    public ChannelEvent createChannelEvent(final String destination, final DigestModel content, final Long commonDistributionConfigId) {
        return new ChannelEvent(destination, contentConverter.getJsonString(content), commonDistributionConfigId);
    }

    public ChannelEvent createChannelEvent(final String destination) {
        return createChannelEvent(destination, getTestMessageModel(), null);
    }
}
