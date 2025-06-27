package com.garganttua.noghost.backend.api.security.authorizations;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.garganttua.api.core.security.authentication.authorization.GGAPIAuthorizationAuthentication;
import com.garganttua.api.core.security.authentication.authorization.GGAPIRefreshAuthorisationAuthentication;
import com.garganttua.api.core.security.authorization.jwt.GGAPIJWTRefreshableAuthorization;
import com.garganttua.api.security.authorizations.protocols.bearer.GGAPISpringSecurityAuthorizationProtocolHttpBearer;
import com.garganttua.api.spec.GGAPIException;
import com.garganttua.api.spec.entity.annotations.GGAPIEntity;
import com.garganttua.api.spec.security.annotations.GGAPIAuthenticator;
import com.garganttua.api.spec.security.annotations.GGAPIAuthorization;
import com.garganttua.api.spec.security.annotations.GGAPIEntitySecurity;
import com.garganttua.api.spec.security.authenticator.GGAPIAuthenticatorScope;

import lombok.extern.slf4j.Slf4j;

@GGAPIEntity(domain = AuthorizationEntity.DOMAIN, interfaces = { "gg:SpringRestInterface" })
@GGAPIAuthorization(signable = true, renewable = true)
@GGAPIAuthenticator(
	interfaces = {"gg:SpringRestAuthenticationInterface"}, 
	authentications = {GGAPIAuthorizationAuthentication.class, GGAPIRefreshAuthorisationAuthentication.class},
	scope = GGAPIAuthenticatorScope.system
)
@GGAPIEntitySecurity(authorizations = {AuthorizationEntity.class}, authorizationProtocols = {GGAPISpringSecurityAuthorizationProtocolHttpBearer.class})
@JsonIgnoreProperties(value = { "gotFromRepository","saveMethod","deleteMethod", "repository", "save", "delete", "engine" })
@Slf4j
public class AuthorizationEntity extends GGAPIJWTRefreshableAuthorization {

	public static final String DOMAIN = "authorizations";

	public AuthorizationEntity(byte[] raw) throws GGAPIException {
		super(raw);
	}

	public AuthorizationEntity() throws GGAPIException {
		super();
	}
	
	public AuthorizationEntity(String uuid, String tenantId, String ownerUuid, List<String> authorities, Date creationDate, Date expirationDate) throws GGAPIException {
		super(uuid, tenantId, ownerUuid, authorities, creationDate, expirationDate);
	}
	
}