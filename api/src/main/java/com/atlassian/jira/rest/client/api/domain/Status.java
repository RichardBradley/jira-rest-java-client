/*
 * Copyright (C) 2010 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.atlassian.jira.rest.client.api.domain;

import com.atlassian.jira.rest.client.api.IdentifiableEntity;
import com.atlassian.jira.rest.client.api.StatusCategory;
import com.google.common.base.Objects;

import java.net.URI;

/**
 * Basic information about a JIRA issue status
 *
 * @since v0.1
 */
public class Status extends AddressableNamedEntity implements IdentifiableEntity<Long> {
    private final Long id;
    private final String description;
    private final URI iconUrl;
    private final StatusCategory statusCategory;

    public Status(URI self, final Long id, final String name, final String description, final URI iconUrl, final StatusCategory statusCategory) {
        super(self, name);
        this.id = id;
        this.description = description;
        this.iconUrl = iconUrl;
        this.statusCategory = statusCategory;
    }

    @Override
    public String toString() {
        return getToStringHelper().
                add("id", id).
                add("description", description).
                add("iconUrl", iconUrl).
                add("statusCategory", statusCategory.toString()).
                toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Status) {
            Status that = (Status) obj;
            return super.equals(obj)
                    && Objects.equal(this.id, that.id)
                    && Objects.equal(this.description, that.description)
                    && Objects.equal(this.iconUrl, that.iconUrl);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, description, iconUrl);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public URI getIconUrl() {
        return iconUrl;
    }

    public StatusCategory getStatusCategory() {
        return statusCategory;
    }
}
