package no.fintlabs.model.personopplysning;

import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.felles.kompleksedatatyper.Periode;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PersonopplysningMappingService {


    public static PersonopplysningResource toResource(Personopplysning personopplysning) {
        PersonopplysningResource personopplysningResource = new PersonopplysningResource();
        Identifikator identifikator = new Identifikator();
        identifikator.setIdentifikatorverdi(personopplysning.getIdentifikatorVerdi());
        personopplysningResource.setKode(personopplysning.getKode());
        personopplysningResource.setNavn(personopplysning.getNavn());
        personopplysningResource.setPassiv(personopplysning.isPassiv());
        personopplysningResource.setGyldighetsperiode(mapToPeriode(personopplysning.getStartGyldighetsdato(), personopplysning.getEndGyldighetsdato(), personopplysning.getBeskrivelseGyldighetsPeriode()));
        personopplysningResource.setSystemId(identifikator);
        return personopplysningResource;
    }

    public static Personopplysning toEntity(PersonopplysningResource personopplysningResource) {
        Personopplysning personopplysning = new Personopplysning();
        personopplysning.setKode(personopplysningResource.getKode());
        personopplysning.setStartGyldighetsdato(personopplysningResource.getGyldighetsperiode().getStart());
        personopplysning.setEndGyldighetsdato(personopplysningResource.getGyldighetsperiode().getSlutt());
        personopplysning.setBeskrivelseGyldighetsPeriode(personopplysningResource.getGyldighetsperiode().getBeskrivelse());
        personopplysning.setNavn(personopplysningResource.getNavn());
        personopplysning.setPassiv(personopplysningResource.getPassiv());
        personopplysning.setIdentifikatorVerdi(UUID.randomUUID().toString());
        return personopplysning;
    }

    private static Periode mapToPeriode(Date startDate, Date endDate, String beskrivelse) {
        Periode periode = new Periode();
        periode.setStart(startDate);
        periode.setSlutt(endDate);
        periode.setBeskrivelse(beskrivelse);
        return periode;
    }
}
