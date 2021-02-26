/*
 * channel-api
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.alert.channel.api.issue.model;

import java.io.Serializable;
import java.util.List;

import com.synopsys.integration.alert.channel.api.issue.search.ExistingIssueDetails;
import com.synopsys.integration.alert.common.rest.model.AlertSerializableModel;

public class IssueCommentModel<T extends Serializable> extends AlertSerializableModel {
    private final ExistingIssueDetails<T> existingIssueDetails;
    private final List<String> comments;

    private final ProjectIssueModel source;

    public IssueCommentModel(ExistingIssueDetails<T> existingIssueDetails, List<String> comments, ProjectIssueModel source) {
        this.existingIssueDetails = existingIssueDetails;
        this.comments = comments;
        this.source = source;
    }

    public ExistingIssueDetails<T> getExistingIssueDetails() {
        return existingIssueDetails;
    }

    public List<String> getComments() {
        return comments;
    }

    public ProjectIssueModel getSource() {
        return source;
    }

}
