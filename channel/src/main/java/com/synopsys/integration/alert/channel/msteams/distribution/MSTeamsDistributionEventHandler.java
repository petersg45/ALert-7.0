package com.synopsys.integration.alert.channel.msteams.distribution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.alert.channel.api.DistributionEventHandler;
import com.synopsys.integration.alert.common.persistence.accessor.MSTeamsJobDetailsAccessor;
import com.synopsys.integration.alert.common.persistence.accessor.ProcessingAuditAccessor;
import com.synopsys.integration.alert.common.persistence.model.job.details.MSTeamsJobDetailsModel;

@Component
public class MSTeamsDistributionEventHandler extends DistributionEventHandler<MSTeamsJobDetailsModel> {
    @Autowired
    public MSTeamsDistributionEventHandler(MSTeamsChannel channel, MSTeamsJobDetailsAccessor jobDetailsAccessor, ProcessingAuditAccessor auditAccessor) {
        super(channel, jobDetailsAccessor, auditAccessor);
    }

}
