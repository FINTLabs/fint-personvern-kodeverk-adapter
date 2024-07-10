package no.fintlabs.model.personopplysning;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Personopplysning {
    @Id
    @Column(nullable = false, unique = true)
    private String kode;

    private Date startGyldighetsdato;

    private Date endGyldighetsdato;

    private String beskrivelseGyldighetsPeriode;

    @Column(nullable = false)
    private String navn;

    private Boolean passiv;

    private String identifikatorVerdi;

}
