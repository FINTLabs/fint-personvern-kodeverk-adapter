package no.fintlabs.model.personopplysning;

import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
import org.springframework.stereotype.Component;

@Component
public class PersonopplysningResourceValidator {

    public boolean isResourceValid(PersonopplysningResource personopplysningResource) {
        if (personopplysningResource.getKode() != null) return false;
        return true;
    }
}
