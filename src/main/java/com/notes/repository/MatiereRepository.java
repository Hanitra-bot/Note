package com.notes.repository;

import com.notes.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Integer> {
    Optional<Matiere> findByLibelleIgnoreCase(String libelle);

    @Query("SELECT DISTINCT m FROM Matiere m LEFT JOIN FETCH m.correcteurs")
    List<Matiere> findAllWithCorrecteurs();
}
