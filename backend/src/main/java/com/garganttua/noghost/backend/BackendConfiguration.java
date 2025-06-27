package com.garganttua.noghost.backend;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.garganttua.api.core.caller.GGAPICaller;
import com.garganttua.api.core.entity.tools.GGAPIEntityHelper;
import com.garganttua.api.spec.GGAPIException;
import com.garganttua.api.spec.GGAPIExceptionCode;
import com.garganttua.api.spec.engine.IGGAPIEngine;
import com.garganttua.api.spec.factory.GGAPIEntityIdentifier;
import com.garganttua.api.spec.factory.IGGAPIEntityFactory;
import com.garganttua.noghost.backend.api.tenants.TenantEntity;

@Configuration
public class BackendConfiguration {

	@Value("${com.garganttua.noghost.backend.superTenantId:0}")
	private static final String SUPER_TENANT_ID = "0";
	@Value("${com.garganttua.noghost.backend.dns:domain.com}")
	private static final String DNS = "domain.com";
	@Inject
	private IGGAPIEngine engine;

	@SuppressWarnings("unchecked")
	@Bean
	public boolean createMasterTenant() throws GGAPIException {
		HashMap<String, String> params = new HashMap<String, String>();
		IGGAPIEntityFactory<TenantEntity> tenantsFactory = (IGGAPIEntityFactory<TenantEntity>) this.engine.getFactory(TenantEntity.DOMAIN_NAME);
		params.put(TenantEntity.USER_LOGIN_PARAMETER_NAME, "super-tenant-login-" + SUPER_TENANT_ID + "@"+DNS);
		params.put(TenantEntity.USER_PASSWORD_PARAMETER_NAME, "super-tenant-password-" + SUPER_TENANT_ID);
		TenantEntity tenantEntity = null;
		try {
			tenantEntity = tenantsFactory.getEntityFromRepository(GGAPICaller.createTenantCaller(SUPER_TENANT_ID),
					params, GGAPIEntityIdentifier.UUID, SUPER_TENANT_ID);
		} catch (GGAPIException e) {
			if (e.getCode() == GGAPIExceptionCode.ENTITY_NOT_FOUND) {
				tenantEntity = TenantEntity.createMasterTenant("The built-in super tenant");
				tenantsFactory.prepareNewEntity(params, tenantEntity, SUPER_TENANT_ID, SUPER_TENANT_ID);
			} else {
				throw e;
			}

		}
		GGAPIEntityHelper.save(tenantEntity, GGAPICaller.createTenantCaller(SUPER_TENANT_ID), params);

		return true;
	}

}
