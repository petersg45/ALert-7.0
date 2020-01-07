/**
 * alert-database
 *
 * Copyright (c) 2020 Synopsys, Inc.
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
package com.synopsys.integration.alert.database.configuration;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.synopsys.integration.alert.database.DatabaseEntity;

@Entity
@Table(schema = "ALERT", name = "REGISTERED_DESCRIPTORS")
public class RegisteredDescriptorEntity extends DatabaseEntity {
    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE_ID")
    private Long typeId;

    @OneToMany
    @JoinColumn(name = "DESCRIPTOR_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private List<DescriptorConfigEntity> descriptorConfigEntities;

    public RegisteredDescriptorEntity() {
        // JPA requires default constructor definitions
    }

    public RegisteredDescriptorEntity(final String name, final Long typeId) {
        this.name = name;
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public Long getTypeId() {
        return typeId;
    }

    public List<DescriptorConfigEntity> getDescriptorConfigEntities() {
        return descriptorConfigEntities;
    }
}
