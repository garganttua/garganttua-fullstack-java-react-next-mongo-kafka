package com.garganttua.noghost.backend.api.tenants;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.garganttua.api.core.caller.GGAPICaller;
import com.garganttua.api.core.engine.GGAPIEngineException;
import com.garganttua.api.core.entity.GenericGGAPIEntity;
import com.garganttua.api.core.entity.exceptions.GGAPIEntityException;
import com.garganttua.api.core.security.authentication.loginpassword.GGAPIAuthenticatorPassword;
import com.garganttua.api.core.security.authentication.loginpassword.GGAPILoginPasswordAuthentication;
import com.garganttua.api.security.authorizations.protocols.bearer.GGAPISpringSecurityAuthorizationProtocolHttpBearer;
import com.garganttua.api.spec.GGAPIExceptionCode;
import com.garganttua.api.spec.caller.IGGAPICaller;
import com.garganttua.api.spec.entity.annotations.GGAPIBusinessAnnotations.GGAPIEntityBeforeCreate;
import com.garganttua.api.spec.entity.annotations.GGAPIEntity;
import com.garganttua.api.spec.entity.annotations.GGAPIEntityAuthorizeUpdate;
import com.garganttua.api.spec.entity.annotations.GGAPIEntityId;
import com.garganttua.api.spec.entity.annotations.GGAPIEntityMandatory;
import com.garganttua.api.spec.entity.annotations.GGAPIEntityOwner;
import com.garganttua.api.spec.entity.annotations.GGAPIEntitySuperOwner;
import com.garganttua.api.spec.entity.annotations.GGAPIEntityUnicity;
import com.garganttua.api.spec.entity.annotations.GGAPIUnicityScope;
import com.garganttua.api.spec.security.annotations.GGAPIAuthenticator;
import com.garganttua.api.spec.security.annotations.GGAPIAuthenticatorAccountNonExpired;
import com.garganttua.api.spec.security.annotations.GGAPIAuthenticatorAccountNonLocked;
import com.garganttua.api.spec.security.annotations.GGAPIAuthenticatorCredentialsNonExpired;
import com.garganttua.api.spec.security.annotations.GGAPIAuthenticatorEnabled;
import com.garganttua.api.spec.security.annotations.GGAPIAuthenticatorKeyUsage;
import com.garganttua.api.spec.security.annotations.GGAPIAuthenticatorLogin;
import com.garganttua.api.spec.security.annotations.GGAPIEntitySecurity;
import com.garganttua.api.spec.security.authenticator.GGAPIAuthenticatorScope;
import com.garganttua.api.spec.security.key.GGAPIKeyAlgorithm;
import com.garganttua.api.spec.service.GGAPIServiceResponseCode;
import com.garganttua.api.spec.service.IGGAPIServiceResponse;
import com.garganttua.noghost.backend.api.security.authorizations.AuthorizationEntity;
import com.garganttua.noghost.backend.api.security.keys.KeyRealmEntity;
import com.garganttua.noghost.backend.api.tools.EmailException;
import com.garganttua.noghost.backend.api.tools.Emails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@GGAPIEntity(domain = UserEntity.DOMAIN_NAME, interfaces = { "gg:SpringRestInterface" }, allow_creation = false, allow_delete_all = false)
@JsonIgnoreProperties(value = { "gotFromRepository", "saveMethod", "deleteMethod", "repository", "save", "delete",
    "engine" })
@GGAPIEntityOwner(ownerId = "uuid", superOwner = "superOwner")
@NoArgsConstructor
@GGAPIAuthenticator(scope = GGAPIAuthenticatorScope.system, interfaces = { "gg:SpringRestAuthenticationInterface" }, authentications = {
    GGAPILoginPasswordAuthentication.class }, authorization = AuthorizationEntity.class, authorizationKey = KeyRealmEntity.class, authorizationKeyUsage = GGAPIAuthenticatorKeyUsage.oneForEach, authorizationKeyAlgorithm = GGAPIKeyAlgorithm.HMAC_SHA512_512, authorizationKeyLifeTime = 30, authorizationKeyLifeTimeUnit = TimeUnit.DAYS, autoCreateAuthorizationKey = true, authorizationLifeTime = 30, authorizationLifeTimeUnit = TimeUnit.MINUTES)
@Getter
@GGAPIEntitySecurity(authorizations = AuthorizationEntity.class, authorizationProtocols = GGAPISpringSecurityAuthorizationProtocolHttpBearer.class)
public class UserEntity extends GenericGGAPIEntity {

  // ID Contains the email

  public static final String DOMAIN_NAME = "users";

  @GGAPIEntityId
  @Setter
  @GGAPIEntityMandatory
  @GGAPIAuthenticatorLogin
  @JsonProperty
  @GGAPIEntityUnicity(scope = GGAPIUnicityScope.system)
  @Getter
  private String id;

  @Getter
  @GGAPIAuthenticatorCredentialsNonExpired
  @GGAPIAuthenticatorEnabled
  @GGAPIAuthenticatorAccountNonExpired
  @GGAPIAuthenticatorAccountNonLocked
  @GGAPIEntityAuthorizeUpdate
  @JsonProperty
  private Boolean active;

  @GGAPIEntitySuperOwner
  private boolean superOwner;

  @GGAPIEntityAuthorizeUpdate
  private UserRoleEntity role;

  @Getter
  @GGAPIEntityMandatory
  @GGAPIEntityAuthorizeUpdate
  @JsonProperty
  @GGAPIAuthenticatorPassword
  private String password;

  @GGAPIEntityAuthorizeUpdate
  private String firstName;

  @GGAPIEntityAuthorizeUpdate
  private String lastName;

  private boolean forceSuperOwner;

  public UserEntity(String userLogin, String userPassword, UserRoleEntity role, String firstName, String lastName,
      boolean superOwner) {
    super(null, userLogin);
    this.id = userLogin;
    this.password = userPassword;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
    this.superOwner = superOwner;
    this.forceSuperOwner = true;
    this.active = true;
  }

  @GGAPIEntityBeforeCreate
  private void beforeCreate(IGGAPICaller caller, Map<String, String> params)
      throws GGAPIEntityException, GGAPIEngineException {
    if (!forceSuperOwner)
      this.superOwner = false;

    // Force active
    this.active = true;

    // Check email format
    try {
      Emails.checkEmail(this.id);
    } catch (EmailException e) {
      throw new GGAPIEntityException(GGAPIExceptionCode.BAD_REQUEST, e.getMessage(), e);
    }

    this.checkRoleExists(params);
  }

  private void checkRoleExists(Map<String, String> params) throws GGAPIEngineException {
    String roleUuid = this.role.getUuid();

    IGGAPIServiceResponse response = this.engine.getService(UserRoleEntity.DOMAIN_NAME)
        .getEntity(GGAPICaller.createTenantCaller(this.tenantId), roleUuid, params);
    if (response.getResponseCode() != GGAPIServiceResponseCode.OK) {
      throw new GGAPIEngineException(GGAPIExceptionCode.BAD_REQUEST, response.getResponse().toString());
    }
  }

  public void setRole(UserRoleEntity role) {
    this.role = role;
  }
}
