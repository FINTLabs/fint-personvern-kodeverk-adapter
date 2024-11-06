package no.fintlabs.model.personopplysning;

import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.felles.kompleksedatatyper.Periode;
import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import no.fintlabs.model.personopplysning.model.PersonopplysningEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonopplysningMappingService {

    public static PersonopplysningResource toResource(PersonopplysningEntity personopplysningEntity) {
        PersonopplysningResource personopplysningResource = new PersonopplysningResource();
        Identifikator identifikator = new Identifikator();
        identifikator.setIdentifikatorverdi(personopplysningEntity.getIdentifikatorVerdi());
        personopplysningResource.setKode(personopplysningEntity.getKode());
        personopplysningResource.setNavn(personopplysningEntity.getNavn());
        personopplysningResource.setPassiv(personopplysningEntity.isPassiv());
        personopplysningResource.setGyldighetsperiode(mapToPeriode(personopplysningEntity.getStartGyldighetsdato(), personopplysningEntity.getEndGyldighetsdato(), personopplysningEntity.getBeskrivelseGyldighetsPeriode()));
        personopplysningResource.setSystemId(identifikator);
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
