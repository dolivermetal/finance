package br.com.doliver.database.converter;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.domain.Person;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonEntityMapper {

  PersonEntity toEntity(Person person);

  Person toDomain(PersonEntity person);

}
