package no.fintlabs.model.personopplysning.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import no.fintlabs.adapter.config.AdapterProperties;
import no.fintlabs.adapter.events.EventPublisher;
import no.fintlabs.adapter.models.RequestFintEvent;
import no.fintlabs.adapter.models.ResponseFintEvent;
import no.fintlabs.adapter.models.SyncPageEntry;
import no.fintlabs.model.personopplysning.PersonopplysningJpaRepository;
import no.fintlabs.model.personopplysning.PersonopplysningRepository;
import no.fintlabs.model.personopplysning.PersonopplysningResourceValidator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class PersonopplysningEventPublisher extends EventPublisher<PersonopplysningResource> {

    private final PersonopplysningResourceValidator personopplysningResourceValidator;

    protected PersonopplysningEventPublisher(WebClient webClient, AdapterProperties adapterProperties, PersonopplysningRepository repository, ObjectMapper objectMapper, PersonopplysningResourceValidator personopplysningResourceValidator, PersonopplysningJpaRepository personopplysningJpaRepository) {
        super("personopplysning", PersonopplysningResource.class, webClient, adapterProperties, repository, objectMapper);
        this.personopplysningResourceValidator = personopplysningResourceValidator;
    }

    @Override
    @Scheduled(initialDelay = 5000, fixedDelay = 50000)
    public void doCheckForNewEvents() {
        checkForNewEvents();
    }

    @Override
    protected void handleEvent(RequestFintEvent requestFintEvent, PersonopplysningResource personopplysningResource) {
        ResponseFintEvent<PersonopplysningResource> response = createResponse(requestFintEvent);
        if (response.getValue() == null) {
            log.error("this bitch empty");
        } else if (personopplysningResourceValidator.isResourceValid(response.getValue().getResource())) {
            PersonopplysningResource savedResource = repository.saveResources(response.getValue().getResource(), requestFintEvent);
            response.setValue(createSyncpageEntry(savedResource));
        } else {
            response.setFailed(true);
            response.setErrorMessage("Failed to handle event");
        }
        submit(response);
    }

    private SyncPageEntry<PersonopplysningResource> createSyncpageEntry(PersonopplysningResource savedResource) {
        return SyncPageEntry.of(savedResource.getSystemId().getIdentifikatorverdi(), savedResource);
    }
}
