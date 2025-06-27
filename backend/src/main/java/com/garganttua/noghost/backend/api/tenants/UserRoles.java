package com.garganttua.noghost.backend.api.tenants;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garganttua.api.spec.engine.IGGAPIEngine;

import jakarta.annotation.PostConstruct;

@Service
public class UserRoles {

	@Autowired
	private IGGAPIEngine engine;

	public UserRoleEntity applyant;

	public UserRoleEntity enterpriseOwner;

	public UserRoleEntity administrator;

	public UserRoleEntity moderator;

	@PostConstruct
	public void createRoles() {
		this.administrator = new UserRoleEntity("administrator", this.getTenantAdminAuthorities());
		this.enterpriseOwner = new UserRoleEntity("enterpriseOwner", this.getTenantAdminAuthorities());
		this.applyant = new UserRoleEntity("applyant", this.getTenantAdminAuthorities());
	}

	private List<String> getTenantAdminAuthorities() {
		return this.engine.getAuthorities();
	}

}
