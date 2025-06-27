package com.garganttua.noghost.backend.api.tenants;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.garganttua.api.spec.dto.annotations.GGAPIDto;
import com.garganttua.api.spec.dto.annotations.GGAPIDtoTenantId;
import com.garganttua.objects.mapper.annotations.GGFieldMappingRule;

@Document(collection = "user-roles")
@GGAPIDto(entityClass = UserRoleEntity.class, db = "gg:SpringMongoDao")
public class UserRoleDto {

  @Id
  @GGFieldMappingRule(sourceFieldAddress = "uuid")
  private String uuid;
  
  @Field
  @GGFieldMappingRule(sourceFieldAddress = "authorities")
  private List<String> authorities;
  
  @Field
  @GGFieldMappingRule(sourceFieldAddress = "id")
  protected String id;
  
  @GGAPIDtoTenantId
  @GGFieldMappingRule(sourceFieldAddress = "tenantId")
  protected String tenantId;
}