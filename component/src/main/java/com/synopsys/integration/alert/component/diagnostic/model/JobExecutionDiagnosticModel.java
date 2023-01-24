package com.synopsys.integration.alert.component.diagnostic.model;

import java.util.List;

import com.synopsys.integration.alert.api.common.model.AlertSerializableModel;
import com.synopsys.integration.alert.common.enumeration.AuditEntryStatus;

public class JobExecutionDiagnosticModel extends AlertSerializableModel {
    private static final long serialVersionUID = -617549382100921292L;
    private final String jobName;
    private final String channelName;
    private final String start;
    private final String end;
    private final AuditEntryStatus status;
    private final int processedNotificationCount;
    private final int totalNotificationCount;

    private final List<JobExecutionStageDiagnosticModel> stages;

    public JobExecutionDiagnosticModel(
        String jobName,
        String channelName,
        String start,
        String end,
        AuditEntryStatus status,
        int processedNotificationCount,
        int totalNotificationCount,
        List<JobExecutionStageDiagnosticModel> stages
    ) {
        this.jobName = jobName;
        this.channelName = channelName;
        this.start = start;
        this.end = end;
        this.status = status;
        this.processedNotificationCount = processedNotificationCount;
        this.totalNotificationCount = totalNotificationCount;
        this.stages = stages;
    }

    public String getJobName() {
        return jobName;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public AuditEntryStatus getStatus() {
        return status;
    }

    public int getProcessedNotificationCount() {
        return processedNotificationCount;
    }

    public int getTotalNotificationCount() {
        return totalNotificationCount;
    }

    public List<JobExecutionStageDiagnosticModel> getStages() {
        return stages;
    }
}
