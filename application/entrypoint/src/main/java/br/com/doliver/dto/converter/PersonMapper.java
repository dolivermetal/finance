package br.com.doliver.dto.converter;

import org.mapstruct.Mapper;

import br.com.doliver.domain.Person;
import br.com.doliver.dto.request.PersonRequest;
import br.com.doliver.dto.response.PersonResponse;

import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {

  Person toDomain(PersonRequest request);

  PersonResponse toResponse(Person person);

}
