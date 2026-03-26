package com.notes.service;
import com.notes.dto.MatiereRequest;
import com.notes.entity.Matiere;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class MatiereService {
    private final MatiereServiceImpl impl;
    public List<Matiere> findAll()                           { return impl.findAll(); }
    public Matiere findById(Integer id)                      { return impl.findById(id); }
    public Matiere create(MatiereRequest req)                { return impl.create(req); }
    public Matiere update(Integer id, MatiereRequest req)    { return impl.update(id, req); }
    public Matiere addCorrecteur(Integer mid, Integer cid)   { return impl.addCorrecteur(mid, cid); }
    public Matiere removeCorrecteur(Integer mid, Integer cid){ return impl.removeCorrecteur(mid, cid); }
    public void delete(Integer id)                           { impl.delete(id); }
}
