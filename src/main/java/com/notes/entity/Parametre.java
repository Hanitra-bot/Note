package com.notes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "parametre")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Parametre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idmatiere", nullable = false)
    private Matiere matiere;

    @NotNull
    @Column(name = "ecart", nullable = false, precision = 5, scale = 2)
    private BigDecimal ecart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idoperation", nullable = false)
    private Operation operation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idresolution", nullable = false)
    private Resolution resolution;
}
