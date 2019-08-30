/**
 * alert-common
 *
 * Copyright (c) 2019 Synopsys, Inc.
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
package com.synopsys.integration.alert.common.action;

import com.synopsys.integration.alert.common.enumeration.ItemOperation;
import com.synopsys.integration.alert.common.exception.AlertException;
import com.synopsys.integration.alert.common.message.model.ComponentItem;
import com.synopsys.integration.alert.common.message.model.LinkableItem;
import com.synopsys.integration.alert.common.message.model.MessageResult;
import com.synopsys.integration.alert.common.message.model.ProviderMessageContent;
import com.synopsys.integration.alert.common.persistence.accessor.FieldAccessor;
import com.synopsys.integration.exception.IntegrationException;

public abstract class TestAction {
    public static final String KEY_CUSTOM_TOPIC = "channel.common.custom.message.topic";
    public static final String KEY_CUSTOM_MESSAGE = "channel.common.custom.message.content";

    public abstract MessageResult testConfig(String configId, String destination, FieldAccessor fieldAccessor) throws IntegrationException;

    public ProviderMessageContent createTestNotificationContent(FieldAccessor fieldAccessor, ItemOperation operation, String messageId) throws AlertException {
        final String customTopic = fieldAccessor.getString(KEY_CUSTOM_TOPIC).orElse("Alert Test Message");
        final String customMessage = fieldAccessor.getString(KEY_CUSTOM_MESSAGE).orElse("Test Message Content");
        ProviderMessageContent.Builder builder = new ProviderMessageContent.Builder();
        builder.applyProvider("Alert");
        builder.applyTopic("Test Topic", customTopic);
        builder.applySubTopic("Test SubTopic", "Test message sent by Alert");
        builder.applyComponentItem(createTestComponentItem(operation, messageId, customMessage));
        return builder.build();
    }

    private ComponentItem createTestComponentItem(ItemOperation operation, String messageId, String customMessage) throws AlertException {
        final ComponentItem.Builder builder = new ComponentItem.Builder();
        builder.applyOperation(operation);
        builder.applyCategory("Test Category");
        builder.applyComponentData("Message ID", messageId);
        builder.applyComponentAttribute(new LinkableItem("Details", customMessage));
        builder.applyNotificationId(1L);
        return builder.build();
    }

}
