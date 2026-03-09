package com.notes.service;

import com.notes.dto.NoteFinaleResultDTO;
import com.notes.entity.*;
import com.notes.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NoteFinaleService {

    private final NoteRepository noteRepository;
    private final ParametreRepository parametreRepository;
    private final NoteFinaleRepository noteFinaleRepository;
    private final CandidatRepository candidatRepository;
    private final MatiereRepository matiereRepository;

    /**
     * Calcule la note finale d'un candidat pour une matière.
     *
     * Algorithme :
     *  1. Récupérer toutes les notes du candidat dans la matière
     *  2. Calculer E = somme des différences absolues entre chaque paire
     *       notes=[9,14]        -> E = |9-14| = 5
     *       notes=[9,14,12]     -> E = |9-14|+|9-12|+|14-12| = 5+3+2 = 10
     *  3. Trouver le premier paramètre de la matière dont  E [signe] seuil  est VRAI
     *  4. Appliquer la résolution : Petit / Grand / Moyenne
     *
     * CAS 1 : signe=>=, seuil=3, résolution=Petit,  notes=[9,14]  -> E=5 >= 3 -> VRAI -> 9
     * CAS 2 : signe=<,  seuil=3, résolution=Grand,  notes=[11,13] -> E=2 <  3 -> VRAI -> 13
     */
    public NoteFinaleResultDTO calculer(Integer candidatId, Integer matiereId) {

        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new NoSuchElementException("Candidat introuvable : " + candidatId));
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new NoSuchElementException("Matière introuvable : " + matiereId));

        List<Note> notes = noteRepository.findByCandidatIdAndMatiereId(candidatId, matiereId);
        if (notes.isEmpty()) {
            throw new IllegalStateException("Aucune note trouvée pour ce candidat dans cette matière.");
        }

        // 1. Calcul de E
        List<BigDecimal> valeurs = notes.stream().map(Note::getNote).toList();
        List<NoteFinaleResultDTO.DiffDetail> details = new ArrayList<>();
        BigDecimal E = BigDecimal.ZERO;

        for (int i = 0; i < valeurs.size(); i++) {
            for (int j = i + 1; j < valeurs.size(); j++) {
                BigDecimal diff = valeurs.get(i).subtract(valeurs.get(j)).abs();
                details.add(NoteFinaleResultDTO.DiffDetail.builder()
                        .note1(valeurs.get(i)).note2(valeurs.get(j)).difference(diff).build());
                E = E.add(diff);
            }
        }

        // 2. Stats
        BigDecimal noteMax = valeurs.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal noteMin = valeurs.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal somme   = valeurs.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal moyenne = somme.divide(BigDecimal.valueOf(valeurs.size()), 2, RoundingMode.HALF_UP);

        // 3. Trouver le paramètre applicable
        List<Parametre> parametres = parametreRepository.findByMatiereId(matiereId);
        if (parametres.isEmpty()) {
            return buildResult(candidat, matiere, valeurs, details, E,
                    BigDecimal.ZERO, "—", "Aucun paramètre configuré",
                    false, noteMax, noteMin, moyenne, null, candidatId, matiereId);
        }

        Parametre match = null;
        for (Parametre p : parametres) {
            if (evaluerCondition(E, p.getOperation().getSigne().trim(), p.getEcart())) {
                match = p;
                break;
            }
        }

        if (match == null) {
            Parametre p0 = parametres.get(0);
            return buildResult(candidat, matiere, valeurs, details, E,
                    p0.getEcart(), p0.getOperation().getSigne(),
                    p0.getResolution().getLibelle(),
                    false, noteMax, noteMin, moyenne, null, candidatId, matiereId);
        }

        // 4. Appliquer la résolution
        BigDecimal noteFinale = switch (match.getResolution().getLibelle().toLowerCase().trim()) {
            case "grand"   -> noteMax;
            case "petit"   -> noteMin;
            case "moyenne" -> moyenne;
            default        -> moyenne;
        };

        return buildResult(candidat, matiere, valeurs, details, E,
                match.getEcart(), match.getOperation().getSigne(),
                match.getResolution().getLibelle(),
                true, noteMax, noteMin, moyenne, noteFinale, candidatId, matiereId);
    }

    private NoteFinaleResultDTO buildResult(
            Candidat candidat, Matiere matiere,
            List<BigDecimal> valeurs, List<NoteFinaleResultDTO.DiffDetail> details,
            BigDecimal E, BigDecimal seuil, String signe, String resolution,
            boolean applicable, BigDecimal noteMax, BigDecimal noteMin,
            BigDecimal moyenne, BigDecimal noteFinale,
            Integer candidatId, Integer matiereId) {

        boolean sauvegardee = noteFinaleRepository
                .findByCandidatIdAndMatiereId(candidatId, matiereId).isPresent();

        return NoteFinaleResultDTO.builder()
                .candidatId(candidat.getId()).candidatNom(candidat.getNom())
                .matiereId(matiere.getId()).matiereLibelle(matiere.getLibelle())
                .notes(valeurs).details(details).totalDiff(E)
                .parametreEcart(seuil).operation(signe).resolution(resolution)
                .parametreApplicable(applicable)
                .noteMax(noteMax).noteMin(noteMin).noteMoyenne(moyenne)
                .noteFinaleCalculee(noteFinale).sauvegardee(sauvegardee).build();
    }

    @Transactional
    public NoteFinale sauvegarder(Integer candidatId, Integer matiereId) {
        NoteFinaleResultDTO result = calculer(candidatId, matiereId);
        if (result.getNoteFinaleCalculee() == null) {
            throw new IllegalStateException("Aucun paramètre applicable — impossible de sauvegarder.");
        }
        Candidat candidat = candidatRepository.findById(candidatId).orElseThrow();
        Matiere  matiere  = matiereRepository.findById(matiereId).orElseThrow();

        NoteFinale nf = noteFinaleRepository
                .findByCandidatIdAndMatiereId(candidatId, matiereId)
                .orElse(NoteFinale.builder().candidat(candidat).matiere(matiere).build());
        nf.setNote(result.getNoteFinaleCalculee());
        return noteFinaleRepository.save(nf);
    }

    private boolean evaluerCondition(BigDecimal E, String signe, BigDecimal seuil) {
        int cmp = E.compareTo(seuil);
        return switch (signe) {
            case "<"  -> cmp < 0;
            case "<=" -> cmp <= 0;
            case ">"  -> cmp > 0;
            case ">=" -> cmp >= 0;
            case "="  -> cmp == 0;
            default   -> false;
        };
    }
}