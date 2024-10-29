package no.fintlabs.model.personopplysning;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import no.fintlabs.adapter.events.WriteableResourceRepository;
import no.fintlabs.adapter.models.RequestFintEvent;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PersonopplysningRepository implements WriteableResourceRepository<PersonopplysningResource> {

    private final PersonopplysningJpaRepository personopplysningJpaRepository;
    private final PersonopplysningMappingService personopplysningMappingService;

    @Override
    public List<PersonopplysningResource> getResources() {
        return personopplysningJpaRepository.findAll().stream()
                .map(e -> personopplysningMappingService.toResource(e))
                .toList();
    }

    @Override
    public List<PersonopplysningResource> getUpdatedResources() {
        return new ArrayList<>();
    }

    @Override
    public PersonopplysningResource saveResources(PersonopplysningResource personopplysningResource, RequestFintEvent requestFintEvent) {
        Personopplysning personopplysning = PersonopplysningMappingService.toEntity(personopplysningResource);
        log.info("Save personopplysning {}", personopplysningResource.getNavn());
        personopplysningJpaRepository.save(personopplysning);
        return personopplysningResource;
    }

}