package com.garganttua.noghost.backend.api.enterprises;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.garganttua.api.core.entity.GenericGGAPIEntity;
import com.garganttua.api.security.authorizations.protocols.bearer.GGAPISpringSecurityAuthorizationProtocolHttpBearer;
import com.garganttua.api.spec.entity.annotations.GGAPIEntity;
import com.garganttua.api.spec.entity.annotations.GGAPIEntityAuthorizeUpdate;
import com.garganttua.api.spec.security.annotations.GGAPIEntitySecurity;
import com.garganttua.noghost.backend.api.security.authorizations.AuthorizationEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@GGAPIEntity(domain = EnterpriseEntity.DOMAIN_NAME, interfaces = { "gg:SpringRestInterface" }, allow_creation = false, allow_update_one = true, allow_delete_all = false)
@JsonIgnoreProperties(value = { "gotFromRepository", "saveMethod", "deleteMethod", "repository", "save", "delete",
    "engine" })
@NoArgsConstructor
@Getter
@GGAPIEntitySecurity(authorizations = AuthorizationEntity.class, authorizationProtocols = GGAPISpringSecurityAuthorizationProtocolHttpBearer.class)
public class EnterpriseEntity extends GenericGGAPIEntity {

    public static final String DOMAIN_NAME = "enterprises";

    @GGAPIEntityAuthorizeUpdate
    private String logoUrl;

    private Date creationDate;

    private String denomination;

    private String siren;

    private String nafCode;

    private String nafCodeReferential;

    private String category;

}
