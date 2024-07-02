package no.fintlabs.model.personopplysning;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import no.fintlabs.adapter.events.WriteableResourceRepository;
import no.fintlabs.adapter.models.RequestFintEvent;
import no.fintlabs.model.personopplysning.model.PersonopplysningEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class PersonopplysningRepository implements WriteableResourceRepository<PersonopplysningResource> {

    private final PersonopplysningJpaRepository personopplysningJpaRepository;

    public PersonopplysningRepository(PersonopplysningJpaRepository personopplysningJpaRepository) {
        this.personopplysningJpaRepository = personopplysningJpaRepository;
    }

    @Override
    public List<PersonopplysningResource> getResources() {
//        return personopplysningJpaRepository.findAll().stream().map(PersonopplysningEntity::toResource).toList();
        return null;
    }

    @Override
    public List<PersonopplysningResource> getUpdatedResources() {
        return new ArrayList<>();
    }

    @Override
    public PersonopplysningResource saveResources(PersonopplysningResource personopplysningResource, RequestFintEvent requestFintEvent) {
//        PersonopplysningEntity entity = PersonopplysningEntity.toEntity(personopplysningResource);
//        personopplysningJpaRepository.save(entity);
        log.info("personopplysning: {} ", personopplysningResource.getNavn());
        return personopplysningResource;

    }
}