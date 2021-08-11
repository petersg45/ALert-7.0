/*
 * alert-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.alert.common.descriptor.config.field;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.alert.api.common.model.AlertSerializableModel;
import com.synopsys.integration.alert.common.descriptor.config.field.validation.ConfigValidationFunction;
import com.synopsys.integration.alert.common.descriptor.config.field.validation.ValidationResult;
import com.synopsys.integration.alert.common.enumeration.FieldType;
import com.synopsys.integration.alert.common.rest.model.FieldModel;
import com.synopsys.integration.alert.common.rest.model.FieldValueModel;

/**
 * <strong>Constructor Parameters:</strong><br/>
 * key         A string to uniquely identify this field throughout the application.<br/>
 * label       A human-readable name for this field.<br/>
 * description An explanation of what this field does.<br/>
 * type        An enum representation of the UI element that should be rendered for this field.<br/>
 * <br/>
 * <strong>Default values:</strong><br/>
 * - required = false<br/>
 * - sensitive = false<br/>
 * - readOnly = false<br/>
 * - panel = ConfigField.FIELD_PANEL_DEFAULT<br/>
 * - header = ConfigField.FIELD_HEADER_EMPTY<br/>
 * - requiredRelatedFields = new HashSet<>()<br/>
 * - disallowedRelatedFields = new HashSet<>()<br/>
 * - defaultValues = new HashSet<>()<br/>
 * - validationFunctions = new LinkedList<>()<br/>
 */
public abstract class ConfigField extends AlertSerializableModel {
    public static final String REQUIRED_FIELD_MISSING = "Required field missing";
    public static final int MAX_FIELD_LENGTH = 2047;
    public static final String FIELD_HEADER_EMPTY = "";
    public static final String FIELD_PANEL_DEFAULT = "";
    public static final String FIELD_LENGTH_LARGE = String.format("Field length is too large (Maximum length of %d).", MAX_FIELD_LENGTH);

    private final String key;
    private final String label;
    private final String description;
    private final String type;

    private final boolean required;
    private final boolean sensitive;

    private final Set<String> requiredRelatedFields;
    private final Set<String> disallowedRelatedFields;
    private final transient List<ConfigValidationFunction> validationFunctions;

    /**
     * @param key         A string to uniquely identify this field throughout the application.
     * @param label       A human-readable name for this field.
     * @param description An explanation of what this field does.
     * @param type        An enum representation of the UI element that should be rendered for this field.
     *                    <p/>
     *                    <strong>Default values:</strong><br/>
     *                    - required = false<br/>
     *                    - sensitive = false<br/>
     *                    - readOnly = false<br/>
     *                    - panel = ConfigField.FIELD_PANEL_DEFAULT<br/>
     *                    - header = ConfigField.FIELD_HEADER_EMPTY<br/>
     *                    - requiredRelatedFields = new HashSet<>()<br/>
     *                    - disallowedRelatedFields = new HashSet<>()<br/>
     *                    - defaultValues = new HashSet<>()<br/>
     *                    - validationFunctions = new LinkedList<>()<br/>
     */
    public ConfigField(String key, String label, String description, FieldType type) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.type = type.getFieldTypeName();
        this.required = false;
        this.sensitive = false;
        this.requiredRelatedFields = new HashSet<>();
        this.disallowedRelatedFields = new HashSet<>();
        this.validationFunctions = new LinkedList<>();
    }

    public ValidationResult validate(FieldValueModel fieldToValidate, FieldModel fieldModel) {
        return validate(fieldToValidate, fieldModel, getValidationFunctions());
    }

    private ValidationResult validate(FieldValueModel fieldToValidate, FieldModel fieldModel, List<ConfigValidationFunction> validationFunctions) {
        ValidationResult errors = ValidationResult.of(validateRequiredField(fieldToValidate), validateLength(fieldToValidate));

        if (!errors.hasErrors()) {
            for (ConfigValidationFunction validation : validationFunctions) {
                if (null != validation) {
                    errors = ValidationResult.of(errors, validation.apply(fieldToValidate, fieldModel));
                }
            }
        }
        return errors;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isSensitive() {
        return sensitive;
    }

    public Set<String> getRequiredRelatedFields() {
        return requiredRelatedFields;
    }

    public Set<String> getDisallowedRelatedFields() {
        return disallowedRelatedFields;
    }

    public List<ConfigValidationFunction> getValidationFunctions() {
        return validationFunctions;
    }

    private ValidationResult validateRequiredField(FieldValueModel fieldToValidate) {
        if (isRequired() && fieldToValidate.containsNoData()) {
            return ValidationResult.errors(REQUIRED_FIELD_MISSING);
        }
        return ValidationResult.success();
    }

    private ValidationResult validateLength(FieldValueModel fieldValueModel) {
        Collection<String> values = fieldValueModel.getValues();
        if (null == values) {
            return ValidationResult.success();
        }

        boolean tooLargeFound = values
            .stream()
            .filter(StringUtils::isNotBlank)
            .anyMatch(value -> MAX_FIELD_LENGTH < value.length());
        if (tooLargeFound) {
            return ValidationResult.errors(FIELD_LENGTH_LARGE);
        }
        return ValidationResult.success();
    }

}
