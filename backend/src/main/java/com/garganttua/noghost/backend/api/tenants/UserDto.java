package com.garganttua.noghost.backend.api.tenants;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.garganttua.api.spec.dto.annotations.GGAPIDto;
import com.garganttua.api.spec.dto.annotations.GGAPIDtoTenantId;
import com.garganttua.objects.mapper.annotations.GGFieldMappingRule;

import lombok.Getter;

@Document(collection = "users")
@GGAPIDto(entityClass = UserEntity.class, db = "gg:SpringMongoDao")
public class UserDto {

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
  
  @Getter
  @Field
  @GGFieldMappingRule(sourceFieldAddress = "firstName")
  private String firstName;
  
  @Getter
  @Field
  @GGFieldMappingRule(sourceFieldAddress = "lastName")
  private String lastName;
  
  @Getter
  @DBRef
  @GGFieldMappingRule(sourceFieldAddress = "role")
  private UserRoleDto role;
  
  @Getter
  @Field
  @GGFieldMappingRule(sourceFieldAddress = "password")
  private String password;
  
  @Getter
  @Field
  @GGFieldMappingRule(sourceFieldAddress = "superOwner")
  private boolean superOwner;
  
  @Field
  @GGFieldMappingRule(sourceFieldAddress = "active")
  private Boolean active;
  
}