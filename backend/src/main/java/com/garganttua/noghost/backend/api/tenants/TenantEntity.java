package com.garganttua.noghost.backend.api.tenants;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.garganttua.api.core.engine.GGAPIEngineException;
import com.garganttua.api.core.entity.GenericGGAPITenantEntity;
import com.garganttua.api.security.authorizations.protocols.bearer.GGAPISpringSecurityAuthorizationProtocolHttpBearer;
import com.garganttua.api.spec.GGAPIException;
import com.garganttua.api.spec.GGAPIExceptionCode;
import com.garganttua.api.spec.caller.IGGAPICaller;
import com.garganttua.api.spec.entity.annotations.GGAPIBusinessAnnotations.GGAPIEntityBeforeCreate;
import com.garganttua.api.spec.entity.annotations.GGAPIBusinessAnnotations.GGAPIEntityBeforeUpdate;
import com.garganttua.api.spec.entity.annotations.GGAPIEntity;
import com.garganttua.api.spec.entity.annotations.GGAPIEntityOwner;
import com.garganttua.api.spec.entity.annotations.GGAPIEntitySuperTenant;
import com.garganttua.api.spec.entity.annotations.GGAPIEntityTenant;
import com.garganttua.api.spec.security.IGGAPISecurityEngine;
import com.garganttua.api.spec.security.annotations.GGAPIEntitySecurity;
import com.garganttua.executor.chain.GGExecutorException;
import com.garganttua.noghost.backend.api.security.authorizations.AuthorizationEntity;

import lombok.NoArgsConstructor;

@GGAPIEntityTenant(tenantId= "uuid")
@GGAPIEntity(domain = TenantEntity.DOMAIN_NAME, interfaces = { "gg:SpringRestInterface" }, allow_creation = false, allow_update_one = false, allow_delete_one = false, allow_delete_all = false)
@JsonIgnoreProperties(value = { "gotFromRepository", "saveMethod", "deleteMethod", "repository", "save", "delete",
		"engine", "forceSuperTenant" })
@NoArgsConstructor
@GGAPIEntitySecurity(authorizations = AuthorizationEntity.class, authorizationProtocols = GGAPISpringSecurityAuthorizationProtocolHttpBearer.class)
public class TenantEntity extends GenericGGAPITenantEntity {

	public static final String DOMAIN_NAME = "tenants";

	public static final String USER_LOGIN_PARAMETER_NAME = "login";
	public static final String USER_PASSWORD_PARAMETER_NAME = "password";

	private boolean forceSuperTenant = false;

	@Inject
	private Optional<IGGAPISecurityEngine> security;

	@Inject
	private UserRoles roles;

	private TenantType type;

	public static TenantEntity createMasterTenant(String id){
		return new TenantEntity(id);
	}

	private TenantEntity(String id) {
		this.type = TenantType.SUPERTENANT;
		this.id = id;
		this.superTenant = true;
		this.forceSuperTenant = true;
	}

	@GGAPIEntityBeforeCreate
	public void beforeCreate(IGGAPICaller caller, Map<String, String> params)
			throws GGAPIException, GGExecutorException {
		if (!forceSuperTenant)
			this.superTenant = false;

		String userLogin = params.get(USER_LOGIN_PARAMETER_NAME);
		String userPassword = params.get(USER_PASSWORD_PARAMETER_NAME);

		if (userLogin == null) {
			throw new GGAPIEngineException(GGAPIExceptionCode.BAD_REQUEST, "No login provided in custom parameters");
		}
		if (userPassword == null) {
			throw new GGAPIEngineException(GGAPIExceptionCode.BAD_REQUEST, "No password provided in custom parameters");
		}

		new TenantEntityCreationExecutor(this, this.engine, this.security, this.roles, userLogin, userPassword).execute();
	}

	@GGAPIEntityBeforeUpdate
	private void beforeUpdate(IGGAPICaller caller, Map<String, String> params)
			throws GGAPIException, GGExecutorException {
		String userLogin = params.get(USER_LOGIN_PARAMETER_NAME);
		String userPassword = params.get(USER_PASSWORD_PARAMETER_NAME);

		new TenantEntityCreationExecutor(this, this.engine, this.security, this.roles, userLogin, userPassword).execute();
	}
}
