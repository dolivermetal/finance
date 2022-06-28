package br.com.doliver.dto.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import br.com.doliver.domain.Outbox;
import br.com.doliver.dto.request.OutboxRequest;
import br.com.doliver.dto.response.OutboxResponse;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutboxEntrypointMapper {

  @Mapping(target = "code", expression = "java(java.util.UUID.randomUUID())")
  Outbox toOutbox(OutboxRequest request);

  OutboxResponse toResponse(Outbox outbox);

}
