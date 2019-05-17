/*
 * Copyright 2019 Jérôme Wacongne.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.test.web.reactive.server;

import static org.springframework.security.test.web.reactive.server.OAuth2SecurityMockServerConfigurers.mockJwt;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.test.support.Defaults;
import org.springframework.security.test.web.reactive.server.OAuth2SecurityMockServerConfigurers.JwtAuthenticationTokenConfigurer;

/**
 * @author Jérôme Wacongne &lt;ch4mp&#64;c4-soft.com&gt;
 */
public class JwtMutatorTests {
	JwtAuthenticationTokenConfigurer authConfigurer;

	@Before
	public void setUp() {
		authConfigurer = mockJwt(new JwtGrantedAuthoritiesConverter())
				.name("ch4mpy").scopes("message:read");
	}

// @formatter:off
	@Test
	public void testDefaultJwtConfigurer() {
		TestController.clientBuilder()
				.apply(mockJwt(new JwtGrantedAuthoritiesConverter())).build()
				.get().uri("/greet").exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo(String.format("Hello, %s!", Defaults.AUTH_NAME));

		TestController.clientBuilder()
				.apply(mockJwt(new JwtGrantedAuthoritiesConverter())).build()
				.get().uri("/authorities").exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("[SCOPE_USER]");
	}

	@Test
	public void testCustomJwtConfigurer() {
		TestController.clientBuilder()
				.apply(authConfigurer).build()
				.get().uri("/greet").exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("Hello, ch4mpy!");

		TestController.clientBuilder()
				.apply(authConfigurer).build()
				.get().uri("/authorities").exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("[SCOPE_message:read]");

		TestController.clientBuilder()
				.apply(authConfigurer)
				.build()
				.get().uri("/jwt").exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo(
						"Hello, ch4mpy! You are sucessfully authenticated and granted with [message:read] scopes using a JavaWebToken.");
	}

	@Test
	public void testCustomJwtMutator() {
		TestController.client()
				.mutateWith(authConfigurer)
				.get().uri("/greet").exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("Hello, ch4mpy!");

		TestController.client()
				.mutateWith(authConfigurer)
				.get().uri("/authorities").exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("[SCOPE_message:read]");

		TestController.client()
				.mutateWith(authConfigurer)
				.get().uri("/jwt").exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo(
						"Hello, ch4mpy! You are sucessfully authenticated and granted with [message:read] scopes using a JavaWebToken.");
	}
// @formatter:on
}
