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

package com.atlassian.jira.rest.client.internal.json;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Collections;

public class SearchResultJsonParser implements JsonObjectParser<SearchResult> {

    @Override
    public SearchResult parse(JSONObject json) throws JSONException {
        if (!json.has("isLast")) {
            throw new JSONException("isLast is required");
        }
        boolean isLast = json.getBoolean("isLast");
        String nextPageToken = json.optString("nextPageToken");
        final JSONArray issuesJsonArray = json.getJSONArray("issues");

        final Iterable<Issue> issues;
        if (issuesJsonArray.length() > 0) {
            // In v 1.2-m01, these two fields were optional, but in version 5.2.2 they are marked as non-optional.
            // I have a JIRA which I think is a "cloud" jira v "1001.0.0-SNAPSHOT" which is not returning these fields
            // The IssueJsonParser accepts null args for these two
            final JSONObject names = JsonParseUtil.getOptionalJsonObject(json, "names");
            final JSONObject schema = JsonParseUtil.getOptionalJsonObject(json, "schema");
            final IssueJsonParser issueParser = new IssueJsonParser(names, schema);
            final GenericJsonArrayParser<Issue> issuesParser = GenericJsonArrayParser.create(issueParser);
            issues = issuesParser.parse(issuesJsonArray);
        } else {
            issues = Collections.emptyList();
        }
        return new SearchResult(issues, isLast, nextPageToken);
    }
}
