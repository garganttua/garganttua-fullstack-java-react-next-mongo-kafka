package com.garganttua.noghost.backend.api.tenants;

import java.util.HashMap;
import java.util.Optional;

import com.garganttua.api.core.caller.GGAPICaller;
import com.garganttua.api.core.entity.tools.GGAPIEntityHelper;
import com.garganttua.api.spec.GGAPIException;
import com.garganttua.api.spec.GGAPIExceptionCode;
import com.garganttua.api.spec.engine.IGGAPIEngine;
import com.garganttua.api.spec.factory.GGAPIEntityIdentifier;
import com.garganttua.api.spec.factory.IGGAPIEntityFactory;
import com.garganttua.api.spec.security.IGGAPISecurityEngine;
import com.garganttua.executor.chain.GGExecutorChain;
import com.garganttua.executor.chain.GGExecutorException;
import com.garganttua.executor.chain.IGGExecutorChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TenantEntityCreationExecutor {

	private TenantEntity entity;
	private IGGExecutorChain<TenantEntity> executorChain;
	private IGGAPIEngine engine;
	private UserRoles roles;
	private IGGAPIEntityFactory<UserRoleEntity> rolesFactory;
	private IGGAPIEntityFactory<UserEntity> usersFactory;
	private String tenantId;
	private Optional<IGGAPISecurityEngine> security;
	private UserRoleEntity tenantAdminRole;
	private String userPassword;
	private String userLogin;
	private UserEntity user;

	@SuppressWarnings("unchecked")
	public TenantEntityCreationExecutor(TenantEntity entity, IGGAPIEngine engine,
			Optional<IGGAPISecurityEngine> security, UserRoles roles, String userLogin, String userPassword) {
		this.security = security;
		this.userLogin = userLogin;
		this.userPassword = userPassword;
		this.tenantId = entity.getUuid();
		this.entity = entity;
		this.engine = engine;
		this.roles = roles;
		this.tenantAdminRole = this.roles.administrator;
		this.rolesFactory = (IGGAPIEntityFactory<UserRoleEntity>) this.engine.getFactory(UserRoleEntity.DOMAIN_NAME);
		this.usersFactory = (IGGAPIEntityFactory<UserEntity>) this.engine.getFactory(UserEntity.DOMAIN_NAME);

		this.executorChain = new GGExecutorChain<TenantEntity>();

		// create admin role
		this.executorChain.addExecutor((team, next) -> {
			this.createOrUpdateRole(entity, this.tenantAdminRole);
			next.execute(entity);
		}, (team, next) -> {
			this.deleteRole(this.tenantAdminRole);
		});

		// create admin user
		this.executorChain.addExecutor((team, next) -> {
			if (this.userLogin != null && !this.userLogin.isEmpty() && this.userPassword != null
					&& !this.userPassword.isEmpty()) {
				this.user = new UserEntity(this.userLogin, this.userPassword, this.tenantAdminRole, "tenant-admin",
						"tenant-admin", true);
				this.createOrUpdateUser(entity, user);
			}
			next.execute(entity);
		}, (team, next) -> {
			this.deleteUser(this.user);
		});

	}

	private void deleteUser(UserEntity user) {
		try {
			GGAPIEntityHelper.delete(user, GGAPICaller.createTenantCaller(this.tenantId),
					new HashMap<String, String>());
		} catch (GGAPIException e) {
			log.atWarn().log("Error", e);
		}
	}

	private void deleteRole(UserRoleEntity role) {
		try {
			GGAPIEntityHelper.delete(role, GGAPICaller.createTenantCaller(this.tenantId),
					new HashMap<String, String>());
		} catch (GGAPIException e) {
			log.atWarn().log("Error", e);
		}
	}

	private void createOrUpdateUser(TenantEntity entity, UserEntity user) throws GGExecutorException {
		user.setTenantId(this.entity.getUuid());
		if (this.security.isPresent()) {
			try {
				this.security.get().authenticatorEntitySecurityPreProcessing(
						GGAPICaller.createTenantCaller(this.tenantId), user, new HashMap<String, String>());
				this.security.get().authenticatorEntitySecurityPostProcessing(
						GGAPICaller.createTenantCaller(this.tenantId), user, new HashMap<String, String>());
			} catch (GGAPIException e) {
				throw new GGExecutorException(e);
			}
		}

		UserEntity userInDb = null;
		try {
			userInDb = this.usersFactory.getEntityFromRepository(GGAPICaller.createTenantCaller(this.tenantId),
					new HashMap<String, String>(), GGAPIEntityIdentifier.ID, user.getId());
		} catch (GGAPIException e) {
			if (e.getCode() != GGAPIExceptionCode.ENTITY_NOT_FOUND) {
				throw new GGExecutorException(e);
			}
		}
		if (userInDb != null) {
			user.setUuid(userInDb.getUuid());
		}
		try {
			this.usersFactory.prepareNewEntity(new HashMap<String, String>(), user, user.getUuid(), this.tenantId);
			GGAPIEntityHelper.save(user, GGAPICaller.createTenantCaller(this.tenantId), new HashMap<String, String>());
		} catch (GGAPIException e) {
			throw new GGExecutorException(e);
		}
	}

	private void createOrUpdateRole(TenantEntity entity, UserRoleEntity role) throws GGExecutorException {
		role.setTenantId(this.entity.getUuid());
		UserRoleEntity roleInDb = null;
		try {
			roleInDb = this.rolesFactory.getEntityFromRepository(GGAPICaller.createTenantCaller(this.tenantId),
					new HashMap<String, String>(), GGAPIEntityIdentifier.ID, role.getId());
		} catch (GGAPIException e) {
			if (e.getCode() != GGAPIExceptionCode.ENTITY_NOT_FOUND) {
				throw new GGExecutorException(e);
			}
		}
		if (roleInDb != null) {
			role.setUuid(roleInDb.getUuid());
		}
		try {
			this.rolesFactory.prepareNewEntity(new HashMap<String, String>(), role, role.getUuid(), this.tenantId);
			GGAPIEntityHelper.save(role, GGAPICaller.createTenantCaller(this.tenantId), new HashMap<String, String>());
		} catch (GGAPIException e) {
			throw new GGExecutorException(e);
		}
	}

	public void execute() throws GGExecutorException {
		this.executorChain.execute(this.entity);
	}
}
