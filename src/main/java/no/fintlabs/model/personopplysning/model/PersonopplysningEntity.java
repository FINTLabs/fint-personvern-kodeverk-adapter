package no.fintlabs.model.personopplysning.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class PersonopplysningEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String identifikatorVerdi;

    private String kode;

    private Date startGyldighetsdato;

    private Date endGyldighetsdato;

    private String beskrivelseGyldighetsPeriode;

    @Column(nullable = false)
    private String navn;

    private boolean passiv;

    private Date startIdentifikatorDato;

    private Date endIdentifikatorDato;
}
