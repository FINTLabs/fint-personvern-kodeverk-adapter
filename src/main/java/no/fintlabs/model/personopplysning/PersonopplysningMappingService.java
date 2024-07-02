//package no.fintlabs.model.personopplysning;
//
//import no.fint.model.resource.personvern.kodeverk.PersonopplysningResource;
//import no.fintlabs.model.personopplysning.model.PersonopplysningEntity;
//
//public class PersonopplysningMappingService {
//
//    public static PersonopplysningResource toResource(PersonopplysningEntity personopplysning) {
//        PersonopplysningResource personopplysningResource = new PersonopplysningResource();
//        personopplysningResource.setKode(personopplysning.kode);
//        personopplysningResource.setNavn(personopplysning.name);
//        personopplysningResource.setPassiv(personopplysning.passiv);
//        personopplysningResource.setGyldighetsperiode(personopplysning.periode);
//        personopplysningResource.setSystemId(personopplysning.systemId);
//        return personopplysningResource;
//    }
//
//    public static PersonopplysningEntity toEntity(PersonopplysningResource personopplysningResource) {
//        PersonopplysningEntity personopplysningEntity = new PersonopplysningEntity();
//        personopplysningEntity.setKode(personopplysningResource.getKode());
//        personopplysningEntity.setPeriode(personopplysningResource.getGyldighetsperiode());
//        personopplysningEntity.setName(personopplysningResource.getNavn());
//        personopplysningEntity.setPassiv(personopplysningResource.getPassiv());
//        personopplysningEntity.setSystemId(personopplysningResource.getSystemId());
//        return personopplysningEntity;
//    }
//
//
//}
