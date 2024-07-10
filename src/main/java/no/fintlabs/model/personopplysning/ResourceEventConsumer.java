package no.fintlabs.model.personopplysning;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.FintLinks;
import no.fintlabs.adapter.config.AdapterProperties;
import no.fintlabs.adapter.events.WriteableResourceRepository;
import no.fintlabs.adapter.models.RequestFintEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;


@Slf4j
public class ResourceEventConsumer<T extends FintLinks> {

    private final WebClient webClient;
    private final AdapterProperties adapterProperties;
    private final String resourceName;
    private final Class<T> resourceClass;
    private final WriteableResourceRepository<T> writeableResourceRepository;
    private final ObjectMapper objectMapper;


    public ResourceEventConsumer(WebClient webClient, AdapterProperties adapterProperties, String resourceName, Class<T> resourceClass, WriteableResourceRepository<T> writeableResourceRepository, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.adapterProperties = adapterProperties;
        this.resourceName = resourceName;
        this.resourceClass = resourceClass;
        this.writeableResourceRepository = writeableResourceRepository;
        this.objectMapper = objectMapper;
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 30000)
    private void getResourceEvent() {
        String uri = "/provider/event%s".formatted(adapterProperties.getCapabilityByResource(resourceName).getEntityUri());
        log.info("Requesting event {}", uri);
        Flux<RequestFintEvent> requestFintEventFlux = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(RequestFintEvent.class);

        requestFintEventFlux.subscribe(requestFintEvent -> {
            writeableResourceRepository.saveResources(mapToResource(requestFintEvent), requestFintEvent);
        });
    }

    private T mapToResource(RequestFintEvent requestFintEvent) {
        try {

            return objectMapper.readValue(requestFintEvent.getValue(), resourceClass);

        } catch (IOException e) {
            log.error("Failed to deserialize request FintEvent", e);
            throw new RuntimeException(e);
        }
    }
}
