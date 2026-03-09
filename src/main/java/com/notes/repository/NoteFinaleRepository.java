package com.notes.repository;

import com.notes.entity.NoteFinale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoteFinaleRepository extends JpaRepository<NoteFinale, Integer> {

    Optional<NoteFinale> findByCandidatIdAndMatiereId(Integer candidatId, Integer matiereId);

    @Query("SELECT nf FROM NoteFinale nf WHERE nf.candidat.id = :candidatId")
    List<NoteFinale> findByCandidatId(@Param("candidatId") Integer candidatId);

    @Query("SELECT nf FROM NoteFinale nf WHERE nf.matiere.id = :matiereId")
    List<NoteFinale> findByMatiereId(@Param("matiereId") Integer matiereId);
}
