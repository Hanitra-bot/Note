package com.notes.service;
import com.notes.dto.CorrecteurRequest;
import com.notes.entity.Correcteur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class CorrecteurService {
    private final CorrecteurServiceImpl impl;
    public List<Correcteur> findAll()                            { return impl.findAll(); }
    public Correcteur findById(Integer id)                       { return impl.findById(id); }
    public Correcteur create(CorrecteurRequest req)              { return impl.create(req); }
    public Correcteur update(Integer id, CorrecteurRequest req)  { return impl.update(id, req); }
    public void delete(Integer id)                               { impl.delete(id); }
}
