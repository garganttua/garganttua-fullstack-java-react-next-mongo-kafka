package com.garganttua.noghost.backend.api.tenants;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.garganttua.api.spec.dto.annotations.GGAPIDto;
import com.garganttua.api.spec.dto.annotations.GGAPIDtoTenantId;
import com.garganttua.objects.mapper.annotations.GGFieldMappingRule;

@GGAPIDto(entityClass = TenantEntity.class, db = "gg:SpringMongoDao")
@Document(collection = "tenants")
public class TenantDto {

	@Id
	@GGFieldMappingRule(sourceFieldAddress = "uuid")
	@GGAPIDtoTenantId
	private String uuid;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "id")
	protected String id;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "superTenant")
	private boolean superTenant;

	@Field
	@GGFieldMappingRule(sourceFieldAddress = "type")
	private TenantType type;

}
