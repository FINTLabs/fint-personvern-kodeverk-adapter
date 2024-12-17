package no.fintlabs.model.personopplysning.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import no.fintlabs.adapter.config.AdapterProperties;
import no.fintlabs.adapter.events.EventPublisher;
import no.fintlabs.adapter.models.event.RequestFintEvent;
import no.fintlabs.adapter.models.event.ResponseFintEvent;
import no.fintlabs.adapter.models.sync.SyncPageEntry;
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
    public void handleEvent(RequestFintEvent requestFintEvent, PersonopplysningResource personopplysningResource) {
        ResponseFintEvent response = createResponse(requestFintEvent);
        response.setOperationType(requestFintEvent.getOperationType());

        try {
            PersonopplysningResource savedResource = repository.saveResources(personopplysningResource, requestFintEvent);
            response.setValue(createSyncpageEntry(savedResource));
        } catch (Exception exception) {
            response.setFailed(true);
            response.setErrorMessage(exception.getMessage());
            log.error("Error in repository.saveResource", exception);
        }

        submit(response);
    }

    private SyncPageEntry createSyncpageEntry(PersonopplysningResource savedResource) {
        return SyncPageEntry.of(savedResource.getSystemId().getIdentifikatorverdi(), savedResource);
    }

}
