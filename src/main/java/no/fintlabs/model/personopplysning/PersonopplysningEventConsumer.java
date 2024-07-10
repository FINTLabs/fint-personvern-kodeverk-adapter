package no.fintlabs.model.personopplysning;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import no.fintlabs.adapter.config.AdapterProperties;
import no.fintlabs.adapter.events.WriteableResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PersonopplysningEventConsumer extends ResourceEventConsumer<PersonopplysningResource> {


    public PersonopplysningEventConsumer(WebClient webClient,
                                         AdapterProperties adapterProperties,
                                         WriteableResourceRepository<PersonopplysningResource> writeableResourceRepository,
                                         ObjectMapper objectMapper) {
        super(webClient, adapterProperties, "personopplysning", PersonopplysningResource.class, writeableResourceRepository, objectMapper);
    }
}
