package com.garganttua.noghost.backend.api.security.keys;

import org.springframework.data.mongodb.core.mapping.Field;

import com.garganttua.api.spec.security.key.GGAPIEncryptionMode;
import com.garganttua.api.spec.security.key.GGAPIEncryptionPaddingMode;
import com.garganttua.api.spec.security.key.GGAPIKeyAlgorithm;
import com.garganttua.api.spec.security.key.GGAPIKeyType;
import com.garganttua.api.spec.security.key.GGAPISignatureAlgorithm;
import com.garganttua.objects.mapper.annotations.GGFieldMappingRule;

public class KeyDto {
	
	@Field
	@GGFieldMappingRule(sourceFieldAddress = "type")
	private GGAPIKeyType type;
	
	@Field
	@GGFieldMappingRule(sourceFieldAddress = "algorithm")
	private GGAPIKeyAlgorithm algorithm;
	
	@Field
	@GGFieldMappingRule(sourceFieldAddress = "rawKey")
	private byte[] key;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "initializationVector")
	private byte[] initializationVector;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "encryptionMode")
	private GGAPIEncryptionMode encryptionMode;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "encryptionPaddingMode")
	private GGAPIEncryptionPaddingMode encryptionPaddingMode;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "signatureAlgorithm")
	private GGAPISignatureAlgorithm signatureAlgorithm;
}
