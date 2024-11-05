package no.fintlabs.model.personopplysning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.felles.kompleksedatatyper.Periode;
import no.fint.model.resource.Link;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import no.fintlabs.model.personopplysning.model.PersonopplysningEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PersonopplysningMappingService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static PersonopplysningResource toResource(PersonopplysningEntity personopplysningEntity) {
        PersonopplysningResource personopplysningResource = new PersonopplysningResource();
        Identifikator identifikator = new Identifikator();
        identifikator.setIdentifikatorverdi(personopplysningEntity.getIdentifikatorVerdi());
        personopplysningResource.setKode(personopplysningEntity.getKode());
        personopplysningResource.setNavn(personopplysningEntity.getNavn());
        personopplysningResource.setPassiv(personopplysningEntity.isPassiv());
        personopplysningResource.setGyldighetsperiode(mapToPeriode(personopplysningEntity.getStartGyldighetsdato(), personopplysningEntity.getEndGyldighetsdato(), personopplysningEntity.getBeskrivelseGyldighetsPeriode()));
        personopplysningResource.setSystemId(identifikator);

        try {
            String links = personopplysningEntity.getLinks();
            personopplysningResource.setLinks(objectMapper.readValue(links, new TypeReference<Map<String, List<Link>>>() {}));
        } catch (Exception e) {
            throw new RuntimeException("Could not parse links" + e.getMessage());
        }

        return personopplysningResource;
    }

    public static PersonopplysningEntity toEntity(PersonopplysningResource personopplysningResource) {
        PersonopplysningEntity personopplysningEntity = new PersonopplysningEntity();
        personopplysningEntity.setKode(personopplysningResource.getKode());
        if (personopplysningResource.getGyldighetsperiode() != null) {
            if (personopplysningResource.getGyldighetsperiode().getStart() != null)
                personopplysningEntity.setStartGyldighetsdato(personopplysningResource.getGyldighetsperiode().getStart());

            if (personopplysningResource.getGyldighetsperiode().getSlutt() != null)
                personopplysningEntity.setEndGyldighetsdato(personopplysningResource.getGyldighetsperiode().getSlutt());
        }
        personopplysningEntity.setBeskrivelseGyldighetsPeriode(personopplysningResource.getGyldighetsperiode().getBeskrivelse());
        personopplysningEntity.setNavn(personopplysningResource.getNavn());
        if (personopplysningResource.getPassiv() != null)
            personopplysningEntity.setPassiv(personopplysningResource.getPassiv());
        personopplysningEntity.setIdentifikatorVerdi(personopplysningResource.getSystemId().getIdentifikatorverdi());

        if(personopplysningResource.getLinks() != null || !personopplysningResource.getLinks().isEmpty()) {
            try {
                Map<String, List<Link>> links = personopplysningResource.getLinks();
                personopplysningEntity.setLinks(objectMapper.writeValueAsString(links));
            } catch (Exception e) {
                throw new RuntimeException("Could not serialize personopplysning links", e);
            }
        }
        return personopplysningEntity;
    }

    private static Periode mapToPeriode(Date startDate, Date endDate, String beskrivelse) {
        Periode periode = new Periode();
        periode.setStart(startDate);
        periode.setSlutt(endDate);
        periode.setBeskrivelse(beskrivelse);
        return periode;
    }
}
