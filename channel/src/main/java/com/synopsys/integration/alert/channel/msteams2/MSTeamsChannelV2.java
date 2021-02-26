/*
 * channel
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.alert.channel.msteams2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.alert.channel.api.MessageBoardChannel;
import com.synopsys.integration.alert.common.persistence.model.job.details.MSTeamsJobDetailsModel;

@Component
public class MSTeamsChannelV2 extends MessageBoardChannel<MSTeamsJobDetailsModel, MSTeamsChannelMessageModel> {
    @Autowired
    protected MSTeamsChannelV2(MSTeamsChannelMessageConverter msTeamsChannelMessageConverter, MSTeamsChannelMessageSender msTeamsChannelMessageSender) {
        super(msTeamsChannelMessageConverter, msTeamsChannelMessageSender);
    }

}
