package no.fintlabs.model.behandlingsgrunnlag;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.personvern.kodeverk.BehandlingsgrunnlagResource;
import no.fintlabs.adapter.config.AdapterProperties;
import no.fintlabs.adapter.datasync.ResourceSubscriber;
import no.fintlabs.adapter.models.AdapterCapability;
import no.fintlabs.adapter.models.SyncPageEntry;
import no.fintlabs.adapter.validator.ValidatorService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class BehandlingsgrunnlagSubscriber extends ResourceSubscriber<BehandlingsgrunnlagResource, BehandlingsgrunnlagPublisher> {

    protected BehandlingsgrunnlagSubscriber(WebClient webClient, AdapterProperties props, BehandlingsgrunnlagPublisher publisher, ValidatorService<BehandlingsgrunnlagResource> validatorService) {
        super(webClient, props, publisher, validatorService);
    }

    @Override
    protected AdapterCapability getCapability() {

        return adapterProperties.getCapabilities().get("behandlingsgrunnlag");
    }

    @Override
    protected SyncPageEntry<BehandlingsgrunnlagResource> createSyncPageEntry(BehandlingsgrunnlagResource resource) {
        String identificationValue = resource.getSystemId().getIdentifikatorverdi();
        return SyncPageEntry.of(identificationValue, resource);
    }
}
