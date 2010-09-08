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

package it;

import com.atlassian.jira.functest.framework.FuncTestCase;
import com.atlassian.jira.restjavaclient.JerseyJiraRestClient;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * TODO: Document this class / interface here
 *
 * @since v0.1
 */
public class AbstractJerseyRestClientTest extends FuncTestCase {
    protected URI jiraUri;
    protected JerseyJiraRestClient client;
    protected URI jiraRestRootUri;

    public AbstractJerseyRestClientTest() {
    }

    public void configureJira() {
        administration.restoreData("jira1-export.xml");
        setUpTest();
    }

    @Override
    protected void setUpTest() {
        System.out.println("!!!!!!!!!!Test Setup");
        try {
            jiraUri = UriBuilder.fromUri(environmentData.getBaseUrl().toURI())/*.path(environmentData.getContext())*/.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        jiraRestRootUri = UriBuilder.fromUri(jiraUri).path("/rest/api/latest/").build();
        System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUU [" + jiraRestRootUri.toString() + "]");
        client = new JerseyJiraRestClient(jiraUri);
    }
}