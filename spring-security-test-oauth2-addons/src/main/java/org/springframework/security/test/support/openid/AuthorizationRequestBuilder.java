package org.springframework.security.test.support.openid;

import java.net.URI;
import java.util.Map;
import java.util.Set;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

public class AuthorizationRequestBuilder {
	private final OAuth2AuthorizationRequest.Builder delegate;
	private final Map<String, Object> additionalParameters;

	public AuthorizationRequestBuilder(
			final AuthorizationGrantType authorizationGrantType,
			final Map<String, Object> additionalParameters) {
		this.additionalParameters = additionalParameters;
		this.delegate = authorizationRequestBuilder(authorizationGrantType)
				.additionalParameters(additionalParameters);
	}

	public AuthorizationRequestBuilder additionalParameter(String name, Object value) {
		additionalParameters.put(name, value);
		return this;
	}

	AuthorizationRequestBuilder attributes(Map<String, Object> attributes) {
		delegate.attributes(attributes);
		return this;
	}

	public AuthorizationRequestBuilder requestUri(URI authorizationRequestUri) {
		delegate.authorizationRequestUri(authorizationRequestUri.toString());
		return this;
	}

	public AuthorizationRequestBuilder authorizationUri(URI authorizationUri) {
		delegate.authorizationUri(authorizationUri.toString());
		return this;
	}

	public AuthorizationRequestBuilder clientId(String clientId) {
		delegate.clientId(clientId);
		return this;
	}

	public AuthorizationRequestBuilder redirectUri(URI redirectUri) {
		delegate.redirectUri(redirectUri.toString());
		return this;
	}

	public AuthorizationRequestBuilder state(String state) {
		delegate.state(state);
		return this;
	}

	AuthorizationRequestBuilder scopes(String... scopes) {
		delegate.scope(scopes);
		return this;
	}

	AuthorizationRequestBuilder scopes(Set<String> scopes) {
		delegate.scopes(scopes);
		return this;
	}

	public OAuth2AuthorizationRequest build() {
		return delegate.build();
	}

	private static OAuth2AuthorizationRequest.Builder authorizationRequestBuilder(AuthorizationGrantType authorizationGrantType) {
		if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(authorizationGrantType)) {
			return OAuth2AuthorizationRequest.authorizationCode();
		}
		if (AuthorizationGrantType.IMPLICIT.equals(authorizationGrantType)) {
			return OAuth2AuthorizationRequest.implicit();
		}
		throw new UnsupportedOperationException(
				"Only authorization_code and implicit grant types are supported for MockOAuth2AuthorizationRequest");
	}
}