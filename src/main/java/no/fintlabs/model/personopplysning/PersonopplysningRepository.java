package no.fintlabs.model.personopplysning;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import no.fintlabs.adapter.events.WriteableResourceRepository;
import no.fintlabs.adapter.models.RequestFintEvent;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class PersonopplysningRepository implements WriteableResourceRepository<PersonopplysningResource> {

    private final PersonopplysningRestTemplate personopplysningRestTemplate;

    public PersonopplysningRepository(PersonopplysningRestTemplate personopplysningRestTemplate) {
        this.personopplysningRestTemplate = personopplysningRestTemplate;
    }

    @Override
    public List<PersonopplysningResource> getResources() {
        return personopplysningRestTemplate.getPersonopplysningResources();
    }

    @Override
    public List<PersonopplysningResource> getUpdatedResources() {
        return new ArrayList<>();
    }

    @Override
    public PersonopplysningResource saveResources(PersonopplysningResource personopplysningResource, RequestFintEvent requestFintEvent) {
        return null;
    }
}