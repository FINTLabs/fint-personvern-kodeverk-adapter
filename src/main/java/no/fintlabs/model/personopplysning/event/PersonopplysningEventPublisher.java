package no.fintlabs.model.personopplysning.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import no.fintlabs.adapter.config.AdapterProperties;
import no.fintlabs.adapter.events.EventPublisher;
import no.fintlabs.adapter.models.OperationType;
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
        response.setOperationType(OperationType.CREATE);
        response.setValue(createSyncpageEntry(personopplysningResource));
        if (personopplysningResourceValidator.isResourceValid(personopplysningResource)) {
            PersonopplysningResource savedResource = repository.saveResources(response.getValue().getResource(), requestFintEvent);
            response.setValue(createSyncpageEntry(savedResource));
            submit(response);
        } else {
            response.setFailed(true);
            response.setErrorMessage("Failed to handle event");
            submit(response);
        }
        log.info(response.getCorrId());
    }

    private SyncPageEntry<PersonopplysningResource> createSyncpageEntry(PersonopplysningResource savedResource) {
        return SyncPageEntry.of(savedResource.getSystemId().getIdentifikatorverdi(), savedResource);
    }

}
