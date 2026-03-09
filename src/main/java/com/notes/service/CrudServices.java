package com.notes.service;

import com.notes.dto.*;
import com.notes.entity.*;
import com.notes.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

// ===== CandidatService =====
@Service
@RequiredArgsConstructor
class CandidatServiceImpl {
    private final CandidatRepository repo;

    public List<Candidat> findAll() { return repo.findAll(); }
    public Candidat findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Candidat introuvable : " + id));
    }
    @Transactional
    public Candidat create(CandidatRequest req) {
        return repo.save(Candidat.builder().nom(req.getNom()).build());
    }
    @Transactional
    public Candidat update(Integer id, CandidatRequest req) {
        Candidat c = findById(id); c.setNom(req.getNom()); return repo.save(c);
    }
    @Transactional
    public void delete(Integer id) { findById(id); repo.deleteById(id); }
    public List<Candidat> search(String q) { return repo.findByNomContainingIgnoreCase(q); }
}

// ===== CorrecteurService =====
@Service
@RequiredArgsConstructor
class CorrecteurServiceImpl {
    private final CorrecteurRepository repo;

    public List<Correcteur> findAll() { return repo.findAll(); }
    public Correcteur findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Correcteur introuvable : " + id));
    }
    @Transactional
    public Correcteur create(CorrecteurRequest req) {
        return repo.save(Correcteur.builder().nom(req.getNom()).build());
    }
    @Transactional
    public Correcteur update(Integer id, CorrecteurRequest req) {
        Correcteur c = findById(id); c.setNom(req.getNom()); return repo.save(c);
    }
    @Transactional
    public void delete(Integer id) { findById(id); repo.deleteById(id); }
}

// ===== MatiereService =====
@Service
@RequiredArgsConstructor
class MatiereServiceImpl {
    private final MatiereRepository repo;
    private final CorrecteurRepository correcteurRepo;

    public List<Matiere> findAll() { return repo.findAllWithCorrecteurs(); }
    public Matiere findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Matière introuvable : " + id));
    }
    @Transactional
    public Matiere create(MatiereRequest req) {
        Matiere m = Matiere.builder().libelle(req.getLibelle()).build();
        if (req.getCorrecteurIds() != null) {
            req.getCorrecteurIds().forEach(cid ->
                m.getCorrecteurs().add(correcteurRepo.findById(cid)
                    .orElseThrow(() -> new NoSuchElementException("Correcteur introuvable : " + cid))));
        }
        return repo.save(m);
    }
    @Transactional
    public Matiere update(Integer id, MatiereRequest req) {
        Matiere m = findById(id);
        m.setLibelle(req.getLibelle());
        if (req.getCorrecteurIds() != null) {
            m.getCorrecteurs().clear();
            req.getCorrecteurIds().forEach(cid ->
                m.getCorrecteurs().add(correcteurRepo.findById(cid)
                    .orElseThrow(() -> new NoSuchElementException("Correcteur introuvable : " + cid))));
        }
        return repo.save(m);
    }
    @Transactional
    public Matiere addCorrecteur(Integer matiereId, Integer correcteurId) {
        Matiere m = findById(matiereId);
        Correcteur c = correcteurRepo.findById(correcteurId)
                .orElseThrow(() -> new NoSuchElementException("Correcteur introuvable : " + correcteurId));
        m.getCorrecteurs().add(c);
        return repo.save(m);
    }
    @Transactional
    public Matiere removeCorrecteur(Integer matiereId, Integer correcteurId) {
        Matiere m = findById(matiereId);
        m.getCorrecteurs().removeIf(c -> c.getId().equals(correcteurId));
        return repo.save(m);
    }
    @Transactional
    public void delete(Integer id) { findById(id); repo.deleteById(id); }
}

// ===== NoteService =====
@Service
@RequiredArgsConstructor
class NoteServiceImpl {
    private final NoteRepository repo;
    private final CandidatRepository candidatRepo;
    private final MatiereRepository matiereRepo;
    private final CorrecteurRepository correcteurRepo;

    public List<Note> findAll() { return repo.findAll(); }
    public Note findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Note introuvable : " + id));
    }
    public List<Note> findByCandidatAndMatiere(Integer candidatId, Integer matiereId) {
        return repo.findByCandidatIdAndMatiereId(candidatId, matiereId);
    }
    @Transactional
    public Note create(NoteRequest req) {
        return repo.save(Note.builder()
                .candidat(candidatRepo.findById(req.getIdCandidat()).orElseThrow())
                .matiere(matiereRepo.findById(req.getIdMatiere()).orElseThrow())
                .correcteur(correcteurRepo.findById(req.getIdCorrecteur()).orElseThrow())
                .note(req.getNote()).build());
    }
    @Transactional
    public Note update(Integer id, NoteRequest req) {
        Note n = findById(id);
        n.setNote(req.getNote());
        n.setCorrecteur(correcteurRepo.findById(req.getIdCorrecteur()).orElseThrow());
        return repo.save(n);
    }
    @Transactional
    public void delete(Integer id) { findById(id); repo.deleteById(id); }
}

// ===== ParametreService =====
@Service
@RequiredArgsConstructor
class ParametreServiceImpl {
    private final ParametreRepository repo;
    private final MatiereRepository matiereRepo;
    private final OperationRepository operationRepo;
    private final ResolutionRepository resolutionRepo;

    public List<Parametre> findAll() { return repo.findAll(); }
    public Parametre findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Paramètre introuvable : " + id));
    }
    public List<Parametre> findByMatiereId(Integer matiereId) { return repo.findByMatiereId(matiereId); }
    @Transactional
    public Parametre create(ParametreRequest req) {
        return repo.save(Parametre.builder()
                .matiere(matiereRepo.findById(req.getIdMatiere()).orElseThrow())
                .operation(operationRepo.findById(req.getIdOperation()).orElseThrow())
                .resolution(resolutionRepo.findById(req.getIdResolution()).orElseThrow())
                .ecart(req.getEcart()).build());
    }
    @Transactional
    public Parametre update(Integer id, ParametreRequest req) {
        Parametre p = findById(id);
        p.setMatiere(matiereRepo.findById(req.getIdMatiere()).orElseThrow());
        p.setOperation(operationRepo.findById(req.getIdOperation()).orElseThrow());
        p.setResolution(resolutionRepo.findById(req.getIdResolution()).orElseThrow());
        p.setEcart(req.getEcart());
        return repo.save(p);
    }
    @Transactional
    public void delete(Integer id) { findById(id); repo.deleteById(id); }
}

// ===== ResolutionService =====
@Service
@RequiredArgsConstructor
class ResolutionServiceImpl {
    private final ResolutionRepository repo;
    public List<Resolution> findAll() { return repo.findAll(); }
    public Resolution findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Résolution introuvable : " + id));
    }
    @Transactional
    public Resolution create(ResolutionRequest req) {
        return repo.save(Resolution.builder().libelle(req.getLibelle()).build());
    }
    @Transactional
    public Resolution update(Integer id, ResolutionRequest req) {
        Resolution r = findById(id); r.setLibelle(req.getLibelle()); return repo.save(r);
    }
    @Transactional
    public void delete(Integer id) { findById(id); repo.deleteById(id); }
}

// ===== OperationService =====
@Service
@RequiredArgsConstructor
class OperationServiceImpl {
    private final OperationRepository repo;
    public List<Operation> findAll() { return repo.findAll(); }
    public Operation findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Opération introuvable : " + id));
    }
    @Transactional
    public Operation create(OperationRequest req) {
        return repo.save(Operation.builder().signe(req.getSigne()).build());
    }
    @Transactional
    public Operation update(Integer id, OperationRequest req) {
        Operation o = findById(id); o.setSigne(req.getSigne()); return repo.save(o);
    }
    @Transactional
    public void delete(Integer id) { findById(id); repo.deleteById(id); }
}
