package no.fintlabs.model.personopplysning.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PersonopplysningEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String kode;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "periode_id", referencedColumnName = "id")
    private PeriodeEntity gyldighetsperiode;

    @Column(nullable = false)
    private String navn;

    private Boolean passiv;

    @Column(nullable = false, unique = true)
    private String systemId;
}
