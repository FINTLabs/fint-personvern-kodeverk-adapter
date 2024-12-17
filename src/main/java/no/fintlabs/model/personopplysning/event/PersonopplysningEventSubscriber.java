package no.fintlabs.model.personopplysning.event;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import no.fintlabs.adapter.config.AdapterProperties;
import no.fintlabs.adapter.events.EventSubscriber;
import no.fintlabs.adapter.models.event.ResponseFintEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class PersonopplysningEventSubscriber extends EventSubscriber<PersonopplysningResource, PersonopplysningEventPublisher> {

    protected PersonopplysningEventSubscriber(WebClient webClient, AdapterProperties adapterProperties, PersonopplysningEventPublisher publisher) {
        super(webClient, adapterProperties, publisher, "personopplysning");
    }

    @Override
    protected void responsePostingEvent(ResponseEntity<Void> responseEntity, ResponseFintEvent responseFintEvent) {
        log.info("Posting response for event: {} status code: {} ", responseFintEvent.getCorrId(), responseEntity.getStatusCode());
    }
}
