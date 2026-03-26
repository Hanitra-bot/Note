package com.notes.service;
import com.notes.dto.ParametreRequest;
import com.notes.entity.Parametre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class ParametreService {
    private final ParametreServiceImpl impl;
    public List<Parametre> findAll()                             { return impl.findAll(); }
    public Parametre findById(Integer id)                        { return impl.findById(id); }
    public List<Parametre> findByMatiereId(Integer id)           { return impl.findByMatiereId(id); }
    public Parametre create(ParametreRequest req)                { return impl.create(req); }
    public Parametre update(Integer id, ParametreRequest req)    { return impl.update(id, req); }
    public void delete(Integer id)                               { impl.delete(id); }
}
