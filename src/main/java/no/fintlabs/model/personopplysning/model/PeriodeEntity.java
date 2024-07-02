package no.fintlabs.model.personopplysning.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PeriodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String beskrivelse;

    private LocalDateTime slutt;

    @Column(nullable = false)
    private LocalDateTime start;
}
