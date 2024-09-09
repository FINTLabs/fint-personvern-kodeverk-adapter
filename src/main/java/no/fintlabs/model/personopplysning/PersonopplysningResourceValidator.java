package no.fintlabs.model.personopplysning;

import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import org.springframework.stereotype.Service;

@Service
public class PersonopplysningResourceValidator {

    public boolean isResourceValid(PersonopplysningResource personopplysningResource) {
        return personopplysningResource.getKode() != null &&
                personopplysningResource.getNavn() != null &&
                personopplysningResource.getSystemId() != null;
    }
}
