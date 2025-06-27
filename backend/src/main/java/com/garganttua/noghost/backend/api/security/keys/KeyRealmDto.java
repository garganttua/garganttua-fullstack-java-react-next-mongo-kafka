package com.garganttua.noghost.backend.api.security.keys;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.garganttua.api.spec.dto.annotations.GGAPIDto;
import com.garganttua.api.spec.dto.annotations.GGAPIDtoTenantId;
import com.garganttua.api.spec.security.key.GGAPIEncryptionMode;
import com.garganttua.api.spec.security.key.GGAPIEncryptionPaddingMode;
import com.garganttua.api.spec.security.key.GGAPIKeyAlgorithm;
import com.garganttua.api.spec.security.key.GGAPIKeyRealmType;
import com.garganttua.api.spec.security.key.GGAPISignatureAlgorithm;
import com.garganttua.objects.mapper.annotations.GGFieldMappingRule;

@GGAPIDto(entityClass = KeyRealmEntity.class, db = "gg:SpringMongoDao")
@Document(collection = "keys")
public class KeyRealmDto {
	@Id
	@GGFieldMappingRule(sourceFieldAddress = "uuid")
	private String uuid;
	
	@Field
	@GGFieldMappingRule(sourceFieldAddress = "id")
	private String id;
	
	@Field
	@GGAPIDtoTenantId
	@GGFieldMappingRule(sourceFieldAddress = "tenantId")
	private String tenantId;
	
	@Field
	@GGFieldMappingRule(sourceFieldAddress = "revoked")
	private boolean revoked;
	
	@Field
	@GGFieldMappingRule(sourceFieldAddress = "expiration")
	private Date expiration;
	
	@Field
	@GGFieldMappingRule(sourceFieldAddress = "ownerId")
	private String ownerId;
	
	@Field
	@GGFieldMappingRule(sourceFieldAddress = "initializationVector")
	private byte[] initializationVector;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "encryptionMode")
	private GGAPIEncryptionMode encryptionMode;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "paddingMode")
	private GGAPIEncryptionPaddingMode paddingMode;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "signatureAlgorithm")
	private GGAPISignatureAlgorithm signatureAlgorithm;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "keyAlgorithm")
	protected GGAPIKeyAlgorithm keyAlgorithm;
	
	@Field
	@GGFieldMappingRule(sourceFieldAddress = "type")
	protected GGAPIKeyRealmType type;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "encryptionKey")
	protected KeyDto encryptionKey;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "decryptionKey")
	protected KeyDto decryptionKey;

}