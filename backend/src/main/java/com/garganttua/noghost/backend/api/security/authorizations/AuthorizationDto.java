package com.garganttua.noghost.backend.api.security.authorizations;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.garganttua.api.spec.dto.annotations.GGAPIDto;
import com.garganttua.api.spec.dto.annotations.GGAPIDtoTenantId;
import com.garganttua.api.spec.security.key.GGAPIKeyAlgorithm;
import com.garganttua.api.spec.security.key.GGAPISignatureAlgorithm;
import com.garganttua.objects.mapper.annotations.GGFieldMappingRule;

@Document(collection = "authorizations")
@GGAPIDto(entityClass = AuthorizationEntity.class, db = "gg:SpringMongoDao")
public class AuthorizationDto {

	@Id
	@GGFieldMappingRule(sourceFieldAddress = "uuid")
	private String uuid;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "id")
	protected String id;

	@Field
	@GGAPIDtoTenantId
	@GGFieldMappingRule(sourceFieldAddress = "tenantId")
	protected String tenantId;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "signature")
	private byte[] signature = null;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "refreshToken")
	private byte[] refreshToken = null;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "refreshTokenExpirationDate")
	private Date refreshTokenExpirationDate = null;

	/*
	 * @DBRef
	 * 
	 * @GGFieldMappingRule(sourceFieldAddress = "key")
	 * protected KeyRealmDto key = null;
	 */

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "ownerId")
	protected String ownerId;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "authorities")
	protected List<String> authorities;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "creationDate")
	protected Date creationDate;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "expirationDate")
	protected Date expirationDate;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "revoked")
	protected Boolean revoked = false;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "alg")
	protected String alg;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "keyAlgorithm")
	protected GGAPIKeyAlgorithm keyAlgorithm;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "signatureAlgorithm")
	protected GGAPISignatureAlgorithm signatureAlgorithm;
}