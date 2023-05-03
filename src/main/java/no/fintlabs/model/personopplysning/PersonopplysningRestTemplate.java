package no.fintlabs.model.personopplysning;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.metamodell.kompleksedatatyper.Attributt;
import no.fint.model.resource.FintLinks;
import no.fint.model.resource.metamodell.KlasseResource;
import no.fint.model.resource.metamodell.KlasseResources;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class PersonopplysningRestTemplate {

    private final RestTemplate restTemplate;
    private List<PersonopplysningResource> personopplysningResources;

    @Value("${fint.metamodell}")
    private String metamodellUri;


    public PersonopplysningRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.personopplysningResources = new ArrayList<>();
    }

    public List<PersonopplysningResource> getPersonopplysningResources() {
        if (personopplysningResources.isEmpty()) {
            updatePersonopplysningResources();
        }
        return personopplysningResources;
    }

    @Scheduled(initialDelay = 1000L, fixedDelay = 3600000L)
    private void updatePersonopplysningResources() {
        log.info("Updating PersonopplysningResource...");

        try {
            KlasseResources resources = restTemplate.getForObject(metamodellUri, KlasseResources.class);

            if (resources == null) {
                throw new NullPointerException("resources is null");
            }

            personopplysningResources = resources.getContent()
                    .stream()
                    .flatMap(klasseResource -> klasseResource.getAttributter()
                            .stream()
                            .map(attributt -> toPersonopplysningResource(attributt, klasseResource)))
                    .collect(Collectors.toList());

            log.info("Found {} PersonopplysningResource", personopplysningResources.size());

        } catch (RestClientException ex) {
            log.error("Update failed: {}", ex.getMessage());
        }
    }

    private PersonopplysningResource toPersonopplysningResource(Attributt attributt, KlasseResource klasseResource) {
        PersonopplysningResource personopplysningResource = new PersonopplysningResource();

        Identifikator systemId = new Identifikator();
        systemId.setIdentifikatorverdi(klasseResource.getId().getIdentifikatorverdi() + "." + attributt.getNavn().toLowerCase());
        personopplysningResource.setSystemId(systemId);
        personopplysningResource.setKode(attributt.getNavn().toLowerCase());
        personopplysningResource.setNavn(StringUtils.capitalize(attributt.getNavn()));
        personopplysningResource.setPassiv(false);

        return personopplysningResource;
    }
}
