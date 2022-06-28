package br.com.doliver.database.converter;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import br.com.doliver.database.entity.CreditCardEntity;
import br.com.doliver.domain.CreditCard;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditCardEntityMapper {

  CreditCardEntity toEntity(CreditCard creditCard);

  CreditCard toDomain(CreditCardEntity account);

}
