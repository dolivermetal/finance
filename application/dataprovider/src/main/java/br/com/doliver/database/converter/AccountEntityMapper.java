package br.com.doliver.database.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import br.com.doliver.database.entity.AccountEntity;
import br.com.doliver.domain.Account;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountEntityMapper {

  @Mapping(target = "person.accounts", ignore = true)
  @Mapping(target = "person.creditCards", ignore = true)
  AccountEntity toEntity(Account account);

  Account toDomain(AccountEntity account);

}
