package no.fintlabs.model.personopplysning;

import no.fintlabs.model.personopplysning.model.PersonopplysningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonopplysningJpaRepository extends JpaRepository<PersonopplysningEntity, String> {
}
