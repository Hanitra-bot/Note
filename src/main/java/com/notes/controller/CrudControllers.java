package com.notes.controller;

import com.notes.dto.*;
import com.notes.entity.*;
import com.notes.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ===== CandidatController =====
@RestController @RequestMapping("/api/candidats") @RequiredArgsConstructor
class CandidatController {
    private final CandidatService service;

    @GetMapping
    public List<Candidat> findAll(@RequestParam(required = false) String search) {
        return search != null ? service.search(search) : service.findAll();
    }
    @GetMapping("/{id}")  public Candidat findById(@PathVariable Integer id) { return service.findById(id); }
    @PostMapping          public ResponseEntity<Candidat> create(@Valid @RequestBody CandidatRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }
    @PutMapping("/{id}")  public Candidat update(@PathVariable Integer id, @Valid @RequestBody CandidatRequest req) { return service.update(id, req); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Integer id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

// ===== CorrecteurController =====
@RestController @RequestMapping("/api/correcteurs") @RequiredArgsConstructor
class CorrecteurController {
    private final CorrecteurService service;

    @GetMapping           public List<Correcteur> findAll() { return service.findAll(); }
    @GetMapping("/{id}")  public Correcteur findById(@PathVariable Integer id) { return service.findById(id); }
    @PostMapping          public ResponseEntity<Correcteur> create(@Valid @RequestBody CorrecteurRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }
    @PutMapping("/{id}")  public Correcteur update(@PathVariable Integer id, @Valid @RequestBody CorrecteurRequest req) { return service.update(id, req); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Integer id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

// ===== MatiereController =====
@RestController @RequestMapping("/api/matieres") @RequiredArgsConstructor
class MatiereController {
    private final MatiereService service;

    @GetMapping           public List<Matiere> findAll() { return service.findAll(); }
    @GetMapping("/{id}")  public Matiere findById(@PathVariable Integer id) { return service.findById(id); }
    @PostMapping          public ResponseEntity<Matiere> create(@Valid @RequestBody MatiereRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }
    @PutMapping("/{id}")  public Matiere update(@PathVariable Integer id, @Valid @RequestBody MatiereRequest req) { return service.update(id, req); }
    @PostMapping("/{id}/correcteurs/{cid}")   public Matiere addCorrecteur(@PathVariable Integer id, @PathVariable Integer cid) { return service.addCorrecteur(id, cid); }
    @DeleteMapping("/{id}/correcteurs/{cid}") public Matiere removeCorrecteur(@PathVariable Integer id, @PathVariable Integer cid) { return service.removeCorrecteur(id, cid); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Integer id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

// ===== NoteController =====
@RestController @RequestMapping("/api/notes") @RequiredArgsConstructor
class NoteController {
    private final NoteService service;

    @GetMapping           public List<Note> findAll() { return service.findAll(); }
    @GetMapping("/{id}")  public Note findById(@PathVariable Integer id) { return service.findById(id); }
    @GetMapping("/candidat/{cId}/matiere/{mId}") public List<Note> findByCandidatAndMatiere(@PathVariable Integer cId, @PathVariable Integer mId) { return service.findByCandidatAndMatiere(cId, mId); }
    @PostMapping          public ResponseEntity<Note> create(@Valid @RequestBody NoteRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }
    @PutMapping("/{id}")  public Note update(@PathVariable Integer id, @Valid @RequestBody NoteRequest req) { return service.update(id, req); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Integer id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

// ===== ParametreController =====
@RestController @RequestMapping("/api/parametres") @RequiredArgsConstructor
class ParametreController {
    private final ParametreService service;

    @GetMapping              public List<Parametre> findAll() { return service.findAll(); }
    @GetMapping("/{id}")     public Parametre findById(@PathVariable Integer id) { return service.findById(id); }
    @GetMapping("/matiere/{mId}") public List<Parametre> findByMatiere(@PathVariable Integer mId) { return service.findByMatiereId(mId); }
    @PostMapping             public ResponseEntity<Parametre> create(@Valid @RequestBody ParametreRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }
    @PutMapping("/{id}")     public Parametre update(@PathVariable Integer id, @Valid @RequestBody ParametreRequest req) { return service.update(id, req); }
    @DeleteMapping("/{id}")  public ResponseEntity<Void> delete(@PathVariable Integer id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

// ===== ResolutionController =====
@RestController @RequestMapping("/api/resolutions") @RequiredArgsConstructor
class ResolutionController {
    private final ResolutionService service;

    @GetMapping           public List<Resolution> findAll() { return service.findAll(); }
    @GetMapping("/{id}")  public Resolution findById(@PathVariable Integer id) { return service.findById(id); }
    @PostMapping          public ResponseEntity<Resolution> create(@Valid @RequestBody ResolutionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }
    @PutMapping("/{id}")  public Resolution update(@PathVariable Integer id, @Valid @RequestBody ResolutionRequest req) { return service.update(id, req); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Integer id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

// ===== OperationController =====
@RestController @RequestMapping("/api/operations") @RequiredArgsConstructor
class OperationController {
    private final OperationService service;

    @GetMapping           public List<Operation> findAll() { return service.findAll(); }
    @GetMapping("/{id}")  public Operation findById(@PathVariable Integer id) { return service.findById(id); }
    @PostMapping          public ResponseEntity<Operation> create(@Valid @RequestBody OperationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }
    @PutMapping("/{id}")  public Operation update(@PathVariable Integer id, @Valid @RequestBody OperationRequest req) { return service.update(id, req); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Integer id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
