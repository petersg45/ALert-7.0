/*
 * channel-jira-server
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.alert.channel.jira.server.distribution;

import java.util.List;
import java.util.stream.Collectors;

import com.synopsys.integration.alert.api.channel.issue.convert.ProjectMessageToIssueModelTransformer;
import com.synopsys.integration.alert.api.channel.jira.distribution.search.JiraIssueAlertPropertiesManager;
import com.synopsys.integration.alert.api.channel.jira.distribution.search.JiraIssueStatusCreator;
import com.synopsys.integration.alert.api.channel.jira.distribution.search.JiraIssueTransitionRetriever;
import com.synopsys.integration.alert.api.channel.jira.distribution.search.JiraSearcher;
import com.synopsys.integration.alert.api.channel.jira.distribution.search.JiraSearcherResponseModel;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.response.TransitionsResponseModel;
import com.synopsys.integration.jira.common.server.model.IssueSearchIssueComponent;
import com.synopsys.integration.jira.common.server.model.IssueSearchResponseModel;
import com.synopsys.integration.jira.common.server.service.IssueSearchService;

public class JiraServerSearcher extends JiraSearcher {
    private final IssueSearchService issueSearchService;
    private final JiraIssueTransitionRetriever jiraIssueTransitionRetriever;

    public JiraServerSearcher(String jiraProjectKey, IssueSearchService issueSearchService, JiraIssueAlertPropertiesManager issuePropertiesManager, ProjectMessageToIssueModelTransformer modelTransformer,
        JiraIssueTransitionRetriever jiraIssueTransitionRetriever, JiraIssueStatusCreator jiraIssueStatusCreator) {
        super(jiraProjectKey, issuePropertiesManager, modelTransformer, jiraIssueStatusCreator);
        this.issueSearchService = issueSearchService;
        this.jiraIssueTransitionRetriever = jiraIssueTransitionRetriever;
    }

    @Override
    protected List<JiraSearcherResponseModel> executeQueryForIssues(String jql) throws IntegrationException {
        IssueSearchResponseModel issueSearchResponseModel = issueSearchService.queryForIssues(jql);
        return issueSearchResponseModel.getIssues()
                   .stream()
                   .map(this::convertModel)
                   .collect(Collectors.toList());
    }

    @Override
    protected TransitionsResponseModel fetchIssueTransitions(String issueKey) throws IntegrationException {
        return jiraIssueTransitionRetriever.fetchIssueTransitions(issueKey);
    }

    private JiraSearcherResponseModel convertModel(IssueSearchIssueComponent issue) {
        String issueUrl = issue.getFields().getIssueType().getSelf();
        String summary = issue.getFields().getSummary();
        return new JiraSearcherResponseModel(issueUrl, issue.getKey(), issue.getId(), summary);
    }

}
