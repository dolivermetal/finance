package br.com.doliver.factory.creditcard;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.doliver.database.entity.CreditCardEntity;
import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.database.repository.CreditCardRepository;
import br.com.doliver.database.repository.PersonRepository;
import br.com.leonardoferreira.jbacon.JBacon;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreditCardFactory extends JBacon<CreditCardEntity> {

  private final CreditCardRepository repository;

  private final PersonRepository personRepository;

  private static final String ALIAS = "Apelido";
  private static final String BRAND = "Bandeira";
  private static final String NAME = "Nome";

  @Override
  protected CreditCardEntity getDefault() {
    CreditCardEntity creditCard = new CreditCardEntity();
    creditCard.setCode(UUID.randomUUID());
    creditCard.setAlias(ALIAS);
    creditCard.setBrand(BRAND);
    creditCard.setCreationDate(LocalDateTime.now());

    PersonEntity personEntity = new PersonEntity();
    personEntity.setCode(UUID.randomUUID());
    personEntity.setName(NAME);
    creditCard.setPerson(personEntity);
    return creditCard;
  }

  @Override
  protected CreditCardEntity getEmpty() {
    return new CreditCardEntity();
  }

  @Override
  protected void persist(final CreditCardEntity creditCard) {
    PersonEntity person = creditCard.getPerson();
    if (Objects.nonNull(person)) {
      personRepository.create(person);
      creditCard.setPerson(person);
    }
    repository.save(creditCard);
  }
}
