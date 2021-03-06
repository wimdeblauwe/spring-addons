# Spring-addons

Set of tools I find useful to work with Spring-framework.
For now it is focused on spring-security with OAuth2, but could grow.

Pick one of the following (remember to define `com.c4-soft.springaddons.version` property):
``` xml
	<dependencies>
		<dependency>
			<groupId>com.c4-soft.springaddons</groupId>
			<artifactId>spring-security-oauth2-addons</artifactId>
			<version>${com.c4-soft.springaddons.version}</version>
		</dependency>
		<dependency>
			<groupId>com.c4-soft.springaddons</groupId>
			<artifactId>spring-security-oauth2-test-addons</artifactId>
			<version>${com.c4-soft.springaddons.version}</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>com.c4-soft.springaddons</groupId>
			<artifactId>spring-security-oauth2-test-webflux-addons</artifactId>
			<version>${com.c4-soft.springaddons.version}</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>com.c4-soft.springaddons</groupId>
			<artifactId>spring-security-oauth2-test-webmvc-addons</artifactId>
			<version>${com.c4-soft.springaddons.version}</version>
			<scope>test</scope>
		</dependency>
	</dependencies>
```

## Release notes

2.0 comes with a noticeable amount of breaking changes. So lets start tracking features.

### 2.0.0
These release is still focused on unit-testing Spring OAuth2 applications
 * `@WithMockAuthentication` annotation along with `mockAuthentication()` servlet (webmvc) and reactive (webflux) flow APIs. You choose the `Authentication` type, the framework feeds the security context with a Mockito mock. This is dead simple but should cover 99% of test cases. I wonder why I didn't think of it sooner...
 * Focus solely on adding to Spring `Authentication` implementations and tests tooling (no more alternatives, with an exception for `OidcId` which overlaps Spring's `OidcIdToken`)
 * Split `webmvc` (servlets) and `webflux` (reactive) code in distinct libs to ease dependency management
 * Re-shuffle packages and jars (less code, less jars, more expressive package names)
 * WIP: Extensives samples and tests. Samples are boot apps under `src/test` to keep jars small
 * Use Keycloak as authorisation-server for all resource-server samples, each of which configuring a specific `Authentication` impl

Note that I chose Keycloak because it's a feature reach, easy to setup authorisation-server.
It should not be much of an effort to migrate sample resource-servers to another one, with an exception of those using `KeycloakAuthenticationToken` as authentication impl, of course. 

## modules

### [`spring-security-oauth2-addons`](https://github.com/ch4mpy/spring-addons/tree/master/spring-security-oauth2-addons)

Some extensions to Spring OAuth2 that could be of use at runtime (not solely tests) such as:
 * new `Authentication` implementations (`OidcIdAuthenticationToken` for instance)
 * builders for `Jwt`, `BearerTokenAuthentication`, ...
 * base classes for Keycloak authentication configuration

### [`spring-security-oauth2-test-addons`](https://github.com/ch4mpy/spring-addons/tree/master/spring-security-test-oauth2-addons)

Code common to webmvc and webflux test libs. This includes annotations, very useful as it allows to tests not only  `@Controller` but also any other kind of `@Component` (such as `@Service`).

### [`spring-security-oauth2-test-webflux-addons`](https://github.com/ch4mpy/spring-addons/tree/master/spring-security-oauth2-test-webflux-addons)

Focus on reactive tests with 
 * "flow" APIs for webflux apps
 * some tooling around `WebTestClient`: configurable default media-type and charset, requests shortcuts

### [`spring-security-oauth2-test-webmvc-addons`](https://github.com/ch4mpy/spring-addons/tree/master/spring-security-oauth2-test-webmvc-addons)

Focus on servlet tests with 
 * "flow" APIs for servlet apps
 * some tooling around `MockMvc`: configurable default media-type and charset, requests shortcuts
