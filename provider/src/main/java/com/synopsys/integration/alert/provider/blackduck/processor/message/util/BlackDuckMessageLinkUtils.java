/*
 * provider
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.alert.provider.blackduck.processor.message.util;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.blackduck.api.generated.view.ProjectVersionComponentView;

public final class BlackDuckMessageLinkUtils {
    public static final String QUERY_PARAM_COMPONENT_NAME = "componentOrVersionName";
    public static final String URI_PIECE_COMPONENTS = "/components";
    public static final int URI_PIECE_COMPONENTS_LENGTH = URI_PIECE_COMPONENTS.length();

    public static String createProjectVersionComponentsLink(ProjectVersionComponentView bomComponent) {
        String bomComponentUrl = bomComponent.getHref().toString();
        int componentsStartIndex = StringUtils.lastIndexOf(bomComponentUrl, URI_PIECE_COMPONENTS);
        if (componentsStartIndex > 0) {
            return StringUtils.substring(bomComponentUrl, 0, componentsStartIndex + URI_PIECE_COMPONENTS_LENGTH);
        }
        return bomComponentUrl;
    }

    public static String createComponentQueryLink(ProjectVersionComponentView bomComponent) {
        String projectVersionComponentsLink = createProjectVersionComponentsLink(bomComponent);
        return String.format("%s?q=%s:%s", projectVersionComponentsLink, QUERY_PARAM_COMPONENT_NAME, bomComponent.getComponentName());
    }

    private BlackDuckMessageLinkUtils() {
    }

}
