package com.notes.repository;

import com.notes.entity.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Integer> {
    List<Candidat> findByNomContainingIgnoreCase(String nom);
}
