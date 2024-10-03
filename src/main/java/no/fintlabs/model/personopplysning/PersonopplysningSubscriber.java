package no.fintlabs.model.personopplysning;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import no.fintlabs.adapter.config.AdapterProperties;
import no.fintlabs.adapter.datasync.ResourceSubscriber;
import no.fintlabs.adapter.models.AdapterCapability;
import no.fintlabs.adapter.models.SyncPageEntry;
import no.fintlabs.adapter.validator.ValidatorService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class PersonopplysningSubscriber extends ResourceSubscriber<PersonopplysningResource, PersonopplysningPublisher> {

    protected PersonopplysningSubscriber(WebClient webClient, AdapterProperties props, PersonopplysningPublisher publisher, ValidatorService<PersonopplysningResource> validatorService) {
        super(webClient, props, publisher, validatorService);
    }

    @Override
    protected AdapterCapability getCapability() {
        return adapterProperties.getCapabilities().get("personopplysning");
    }

    @Override
    protected SyncPageEntry<PersonopplysningResource> createSyncPageEntry(PersonopplysningResource resource) {
        String identificationValue = resource.getSystemId().getIdentifikatorverdi();
        return SyncPageEntry.of(identificationValue, resource);
    }
}
