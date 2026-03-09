package com.notes.repository;

import com.notes.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query("SELECT n FROM Note n WHERE n.candidat.id = :candidatId AND n.matiere.id = :matiereId")
    List<Note> findByCandidatIdAndMatiereId(@Param("candidatId") Integer candidatId,
                                             @Param("matiereId") Integer matiereId);

    @Query("SELECT n FROM Note n WHERE n.candidat.id = :candidatId")
    List<Note> findByCandidatId(@Param("candidatId") Integer candidatId);
}
