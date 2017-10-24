/**
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
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
package com.blackducksoftware.integration.hub.alert.web.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class EmailConfigRestModel implements Serializable {
    private static final long serialVersionUID = 9172607945030111585L;

    private Long id;

    // JavaMail properties http://connector.sourceforge.net/doc-files/Properties.html
    private String mailSmtpHost;
    private String mailSmtpUser;
    // not a javamail property, but we are going to piggy-back to get the smtp password
    private String mailSmtpPassword;
    private Integer mailSmtpPort;
    private Integer mailSmtpConnectionTimeout;
    private Integer mailSmtpTimeout;
    private String mailSmtpFrom;
    private String mailSmtpLocalhost;
    private Boolean mailSmtpEhlo;
    private Boolean mailSmtpAuth;
    private String mailSmtpDnsNotify;
    private String mailSmtpDsnRet;
    private Boolean mailSmtpAllow8bitmime;
    private Boolean mailSmtpSendPartial;
    private String emailTemplateDirectory;
    private String emailTemplateLogoImage;

    protected EmailConfigRestModel() {
    }

    public EmailConfigRestModel(final Long id, final String mailSmtpHost, final String mailSmtpUser, final String mailSmtpPassword, final Integer mailSmtpPort, final Integer mailSmtpConnectionTimeout, final Integer mailSmtpTimeout,
            final String mailSmtpFrom, final String mailSmtpLocalhost, final Boolean mailSmtpEhlo, final Boolean mailSmtpAuth, final String mailSmtpDnsNotify, final String mailSmtpDsnRet, final Boolean mailSmtpAllow8bitmime,
            final Boolean mailSmtpSendPartial, final String emailTemplateDirectory, final String emailTemplateLogoImage) {
        this.id = id;
        this.mailSmtpHost = mailSmtpHost;
        this.mailSmtpUser = mailSmtpUser;
        this.mailSmtpPassword = mailSmtpPassword;
        this.mailSmtpPort = mailSmtpPort;
        this.mailSmtpConnectionTimeout = mailSmtpConnectionTimeout;
        this.mailSmtpTimeout = mailSmtpTimeout;
        this.mailSmtpFrom = mailSmtpFrom;
        this.mailSmtpLocalhost = mailSmtpLocalhost;
        this.mailSmtpEhlo = mailSmtpEhlo;
        this.mailSmtpAuth = mailSmtpAuth;
        this.mailSmtpDnsNotify = mailSmtpDnsNotify;
        this.mailSmtpDsnRet = mailSmtpDsnRet;
        this.mailSmtpAllow8bitmime = mailSmtpAllow8bitmime;
        this.mailSmtpSendPartial = mailSmtpSendPartial;
        this.emailTemplateDirectory = emailTemplateDirectory;
        this.emailTemplateLogoImage = emailTemplateLogoImage;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getMailSmtpHost() {
        return mailSmtpHost;
    }

    public void setMailSmtpHost(final String mailSmtpHost) {
        this.mailSmtpHost = mailSmtpHost;
    }

    public String getMailSmtpUser() {
        return mailSmtpUser;
    }

    public void setMailSmtpUser(final String mailSmtpUser) {
        this.mailSmtpUser = mailSmtpUser;
    }

    public String getMailSmtpPassword() {
        return mailSmtpPassword;
    }

    public void setMailSmtpPassword(final String mailSmtpPassword) {
        this.mailSmtpPassword = mailSmtpPassword;
    }

    public Integer getMailSmtpPort() {
        return mailSmtpPort;
    }

    public void setMailSmtpPort(final Integer mailSmtpPort) {
        this.mailSmtpPort = mailSmtpPort;
    }

    public Integer getMailSmtpConnectionTimeout() {
        return mailSmtpConnectionTimeout;
    }

    public void setMailSmtpConnectionTimeout(final Integer mailSmtpConnectionTimeout) {
        this.mailSmtpConnectionTimeout = mailSmtpConnectionTimeout;
    }

    public Integer getMailSmtpTimeout() {
        return mailSmtpTimeout;
    }

    public void setMailSmtpTimeout(final Integer mailSmtpTimeout) {
        this.mailSmtpTimeout = mailSmtpTimeout;
    }

    public String getMailSmtpFrom() {
        return mailSmtpFrom;
    }

    public void setMailSmtpFrom(final String mailSmtpFrom) {
        this.mailSmtpFrom = mailSmtpFrom;
    }

    public String getMailSmtpLocalhost() {
        return mailSmtpLocalhost;
    }

    public void setMailSmtpLocalhost(final String mailSmtpLocalhost) {
        this.mailSmtpLocalhost = mailSmtpLocalhost;
    }

    public Boolean getMailSmtpEhlo() {
        return mailSmtpEhlo;
    }

    public void setMailSmtpEhlo(final Boolean mailSmtpEhlo) {
        this.mailSmtpEhlo = mailSmtpEhlo;
    }

    public Boolean getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public void setMailSmtpAuth(final Boolean mailSmtpAuth) {
        this.mailSmtpAuth = mailSmtpAuth;
    }

    public String getMailSmtpDnsNotify() {
        return mailSmtpDnsNotify;
    }

    public void setMailSmtpDnsNotify(final String mailSmtpDnsNotify) {
        this.mailSmtpDnsNotify = mailSmtpDnsNotify;
    }

    public String getMailSmtpDsnRet() {
        return mailSmtpDsnRet;
    }

    public void setMailSmtpDsnRet(final String mailSmtpDsnRet) {
        this.mailSmtpDsnRet = mailSmtpDsnRet;
    }

    public Boolean getMailSmtpAllow8bitmime() {
        return mailSmtpAllow8bitmime;
    }

    public void setMailSmtpAllow8bitmime(final Boolean mailSmtpAllow8bitmime) {
        this.mailSmtpAllow8bitmime = mailSmtpAllow8bitmime;
    }

    public Boolean getMailSmtpSendPartial() {
        return mailSmtpSendPartial;
    }

    public void setMailSmtpSendPartial(final Boolean mailSmtpSendPartial) {
        this.mailSmtpSendPartial = mailSmtpSendPartial;
    }

    public String getEmailTemplateDirectory() {
        return emailTemplateDirectory;
    }

    public void setEmailTemplateDirectory(final String emailTemplateDirectory) {
        this.emailTemplateDirectory = emailTemplateDirectory;
    }

    public String getEmailTemplateLogoImage() {
        return emailTemplateLogoImage;
    }

    public void setEmailTemplateLogoImage(final String emailTemplateLogoImage) {
        this.emailTemplateLogoImage = emailTemplateLogoImage;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this, RecursiveToStringStyle.JSON_STYLE);
        reflectionToStringBuilder.setExcludeFieldNames("mailSmtpPassword");
        return reflectionToStringBuilder.toString();
    }
}
