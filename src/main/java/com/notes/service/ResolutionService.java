package com.notes.service;
import com.notes.dto.ResolutionRequest;
import com.notes.entity.Resolution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class ResolutionService {
    private final ResolutionServiceImpl impl;
    public List<Resolution> findAll()                              { return impl.findAll(); }
    public Resolution findById(Integer id)                         { return impl.findById(id); }
    public Resolution create(ResolutionRequest req)                { return impl.create(req); }
    public Resolution update(Integer id, ResolutionRequest req)    { return impl.update(id, req); }
    public void delete(Integer id)                                 { impl.delete(id); }
}
