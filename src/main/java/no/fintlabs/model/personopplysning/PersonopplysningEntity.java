package no.fintlabs.model.personopplysning;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class PersonopplysningEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String kode;

    private Date startGyldighetsdato;

    private Date endGyldighetsdato;

    private String beskrivelseGyldighetsPeriode;

    @Column(nullable = false)
    private String navn;

    private boolean passiv;

    private String identifikatorVerdi;

    private Date startIdentifikatorDato;

    private Date endIdentifikatorDato;
}
