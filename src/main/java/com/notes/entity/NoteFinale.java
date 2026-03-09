package com.notes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "note_finale",
       uniqueConstraints = @UniqueConstraint(columnNames = {"idcandidat", "idmatiere"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NoteFinale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcandidat", nullable = false)
    private Candidat candidat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idmatiere", nullable = false)
    private Matiere matiere;

    @DecimalMin("0.00") @DecimalMax("20.00")
    @Column(precision = 5, scale = 2)
    private BigDecimal note;
}
