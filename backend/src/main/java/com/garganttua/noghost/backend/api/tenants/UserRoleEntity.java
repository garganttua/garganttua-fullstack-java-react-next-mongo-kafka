package com.garganttua.noghost.backend.api.tenants;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.garganttua.api.core.entity.GenericGGAPIEntity;
import com.garganttua.api.security.authorizations.protocols.bearer.GGAPISpringSecurityAuthorizationProtocolHttpBearer;
import com.garganttua.api.spec.entity.annotations.GGAPIEntity;
import com.garganttua.api.spec.entity.annotations.GGAPIEntityAuthorizeUpdate;
import com.garganttua.api.spec.security.annotations.GGAPIAuthenticatorAuthorities;
import com.garganttua.api.spec.security.annotations.GGAPIEntitySecurity;
import com.garganttua.noghost.backend.api.security.authorizations.AuthorizationEntity;

import lombok.NoArgsConstructor;
import lombok.Setter;

@GGAPIEntity(
    domain = UserRoleEntity.DOMAIN_NAME, 
    interfaces = { "gg:SpringRestInterface" },
    allow_creation = false,
    allow_update_one = false,
    allow_delete_all = false,
    allow_delete_one = false
)
@JsonIgnoreProperties(value = { "gotFromRepository","saveMethod","deleteMethod", "repository", "save", "delete", "engine" })
@GGAPIEntitySecurity(authorizations = {AuthorizationEntity.class}, authorizationProtocols = {GGAPISpringSecurityAuthorizationProtocolHttpBearer.class})
@NoArgsConstructor
public class UserRoleEntity extends GenericGGAPIEntity {
  
  public static final String DOMAIN_NAME = "userroles";
  
  @JsonProperty
  @GGAPIAuthenticatorAuthorities
  @GGAPIEntityAuthorizeUpdate
  @Setter
  private List<String> authorities;

  public UserRoleEntity(String uuid, String id, String roleName, List<String> authorities) {
    super(uuid, id);
    this.id = roleName;
    this.authorities = authorities;   
  }
  
  public UserRoleEntity(String roleName, List<String> authorities) {
    this(null, null, roleName, authorities);
  }
}
