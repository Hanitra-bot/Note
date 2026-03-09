package com.notes.service;
import com.notes.dto.CandidatRequest;
import com.notes.entity.Candidat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class CandidatService {
    private final CandidatServiceImpl impl;
    public List<Candidat> findAll()                          { return impl.findAll(); }
    public Candidat findById(Integer id)                     { return impl.findById(id); }
    public Candidat create(CandidatRequest req)              { return impl.create(req); }
    public Candidat update(Integer id, CandidatRequest req)  { return impl.update(id, req); }
    public void delete(Integer id)                           { impl.delete(id); }
    public List<Candidat> search(String q)                   { return impl.search(q); }
}
