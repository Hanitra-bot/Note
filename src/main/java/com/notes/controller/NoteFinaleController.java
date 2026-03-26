package com.notes.controller;

import com.notes.dto.NoteFinaleResultDTO;
import com.notes.entity.NoteFinale;
import com.notes.repository.NoteFinaleRepository;
import com.notes.service.NoteFinaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/note-finale")
@RequiredArgsConstructor
public class NoteFinaleController {

    private final NoteFinaleService service;
    private final NoteFinaleRepository repository;

    @GetMapping("/calculer")
    public ResponseEntity<NoteFinaleResultDTO> calculer(
            @RequestParam Integer candidatId, @RequestParam Integer matiereId) {
        return ResponseEntity.ok(service.calculer(candidatId, matiereId));
    }

    @PostMapping("/sauvegarder")
    public ResponseEntity<NoteFinale> sauvegarder(
            @RequestParam Integer candidatId, @RequestParam Integer matiereId) {
        return ResponseEntity.ok(service.sauvegarder(candidatId, matiereId));
    }

    @GetMapping                         public List<NoteFinale> findAll()                        { return repository.findAll(); }
    @GetMapping("/candidat/{candidatId}") public List<NoteFinale> findByCandidat(@PathVariable Integer candidatId) { return repository.findByCandidatId(candidatId); }
    @GetMapping("/matiere/{matiereId}")   public List<NoteFinale> findByMatiere(@PathVariable Integer matiereId)   { return repository.findByMatiereId(matiereId); }
    @DeleteMapping("/{id}")               public ResponseEntity<Void> delete(@PathVariable Integer id) { repository.deleteById(id); return ResponseEntity.noContent().build(); }
}
