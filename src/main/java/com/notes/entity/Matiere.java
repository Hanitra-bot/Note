package com.notes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "matiere")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 150)
    private String libelle;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "matiere_correcteur",
        joinColumns = @JoinColumn(name = "idmatiere"),
        inverseJoinColumns = @JoinColumn(name = "idcorrecteur")
    )
    @Builder.Default
    private Set<Correcteur> correcteurs = new HashSet<>();
}
