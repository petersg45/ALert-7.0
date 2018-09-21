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
package com.synopsys.integration.alert.common.field;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;

import com.google.gson.reflect.TypeToken;
import com.synopsys.integration.alert.common.enumeration.FieldContentIdentifier;

public class StringHierarchicalField extends HierarchicalField {
    private static final Type TYPE = new TypeToken<String>() {}.getType();

    private final String configNameMapping;

    public StringHierarchicalField(final Collection<String> pathToField, final String innerMostFieldName, final FieldContentIdentifier contentIdentifier, final String label) {
        super(pathToField, innerMostFieldName, contentIdentifier, label, TYPE);
        this.configNameMapping = null;
    }

    public StringHierarchicalField(final Collection<String> pathToField, final String innerMostFieldName, final FieldContentIdentifier contentIdentifier, final String label, final String configNameMapping) {
        super(pathToField, innerMostFieldName, contentIdentifier, label, TYPE);
        this.configNameMapping = configNameMapping;
    }

    public Optional<String> getConfigNameMapping() {
        return Optional.ofNullable(configNameMapping);
    }

    public boolean isFilterable() {
        return configNameMapping != null;
    }
}
