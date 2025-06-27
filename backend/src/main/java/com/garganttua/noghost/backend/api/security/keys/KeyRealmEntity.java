package com.garganttua.noghost.backend.api.security.keys;

import java.util.Date;

import com.garganttua.api.core.security.key.GGAPIKeyRealm;
import com.garganttua.api.security.authorizations.protocols.bearer.GGAPISpringSecurityAuthorizationProtocolHttpBearer;
import com.garganttua.api.spec.entity.annotations.GGAPIEntity;
import com.garganttua.api.spec.security.annotations.GGAPIEntitySecurity;
import com.garganttua.api.spec.security.key.GGAPIEncryptionMode;
import com.garganttua.api.spec.security.key.GGAPIEncryptionPaddingMode;
import com.garganttua.api.spec.security.key.GGAPIKeyAlgorithm;
import com.garganttua.api.spec.security.key.GGAPISignatureAlgorithm;
import com.garganttua.noghost.backend.api.security.authorizations.AuthorizationEntity;

@GGAPIEntity(domain = KeyRealmEntity.domain, interfaces = { "gg:SpringRestInterface" })
@GGAPIEntitySecurity(authorizations = {AuthorizationEntity.class}, authorizationProtocols = {GGAPISpringSecurityAuthorizationProtocolHttpBearer.class})
public class KeyRealmEntity extends GGAPIKeyRealm {
	
	public static final String domain = "keys";
	
	public KeyRealmEntity() {
		super(null, null, null, null, null, null);
	}

	public KeyRealmEntity(String keyRealmName, GGAPIKeyAlgorithm keyAlgorithm, Date expiration, GGAPIEncryptionMode encryptionMode,
			GGAPIEncryptionPaddingMode paddingMode, GGAPISignatureAlgorithm signatureAlgorithm) {
		super(keyRealmName, keyAlgorithm, expiration, encryptionMode, paddingMode, signatureAlgorithm);
	}
}
