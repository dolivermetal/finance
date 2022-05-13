package br.com.doliver.dto.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.doliver.domain.Outbox;
import br.com.doliver.dto.request.OutboxRequest;
import br.com.doliver.dto.response.OutboxResponse;

@Mapper(componentModel = "spring")
public interface OutboxEntrypointMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "integrationStatus", ignore = true)
  @Mapping(target = "datCreation", ignore = true)
  Outbox toOutbox(OutboxRequest request);

  OutboxResponse toResponse(Outbox outbox);

}
