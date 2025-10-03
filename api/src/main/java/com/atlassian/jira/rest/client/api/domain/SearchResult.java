/*
 * Copyright (C) 2011 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.atlassian.jira.rest.client.api.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Represents search results - links to issues matching given filter (JQL query) with basic
 * information supporting the paging through the results.
 *
 * https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-search/#api-rest-api-3-search-jql-get
 *
 * @since v0.2
 */
public class SearchResult {
    private final Iterable<Issue> issues;
    private final boolean isLast;
    private final String nextPageToken;

    public SearchResult(Iterable<Issue> issues, boolean isLast, String nextPageToken) {
        this.issues = issues;
        this.isLast = isLast;
        this.nextPageToken = nextPageToken;
    }

    public Iterable<Issue> getIssues() {
        return issues;
    }

    public boolean isLast() {
        return isLast;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "issues=" + issues +
                ", isLast=" + isLast +
                ", nextPageToken='" + nextPageToken + '\'' +
                '}';
    }
}
