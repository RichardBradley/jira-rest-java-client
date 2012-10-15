/*
 * Copyright (C) 2012 Atlassian
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
package com.atlassian.jira.rest.client.internal.json;

import com.atlassian.jira.rest.client.TestUtil;
import com.atlassian.jira.rest.client.domain.RoleActor;
import junit.framework.Assert;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

public class RoleActorJsonParserTest {

	private final RoleActorJsonParser roleActorJsonParser;
	private URI baseJiraURI = TestUtil.toUri("http://localhost:2990");

	public RoleActorJsonParserTest() throws URISyntaxException {
		this.roleActorJsonParser = new RoleActorJsonParser(baseJiraURI);
	}

	@Test
	public void testParseValidActorWithOptionalParam() throws Exception {
		RoleActor actor =
				roleActorJsonParser.parse(ResourceUtil.getJsonObjectFromResource("/json/actor/valid-actor.json"));
		Assert.assertEquals(10020l, actor.getId().longValue());
		Assert.assertEquals("jira-users", actor.getName());
		Assert.assertEquals("jira-users", actor.getDisplayName());
		Assert.assertEquals("atlassian-group-role-actor", actor.getType());
		Assert.assertEquals(TestUtil.buildURI(baseJiraURI, "/jira/secure/useravatar?size=small&avatarId=10083"), actor.getAvatarUri());
	}

	@Test
	public void testParseValidActorWithoutOptionalParams() throws JSONException {
		RoleActor actor =
				roleActorJsonParser.parse(ResourceUtil.getJsonObjectFromResource("/json/actor/valid-actor-without-avatar.json"));
		Assert.assertEquals("jira-users", actor.getName());
		Assert.assertEquals("jira-users", actor.getDisplayName());
		Assert.assertEquals("atlassian-group-role-actor", actor.getType());
	}

	@Test(expected = JSONException.class)
	public void testParseInvalidActor() throws Exception {
		roleActorJsonParser.parse(ResourceUtil.getJsonObjectFromResource("/json/actor/invalid-actor-without-required-fields.json"));
		roleActorJsonParser.parse(ResourceUtil.getJsonObjectFromResource("/json/actor/invalid-actor-without-required-fields.json"));
	}
}