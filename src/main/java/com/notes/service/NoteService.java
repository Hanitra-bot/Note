package com.notes.service;
import com.notes.dto.NoteRequest;
import com.notes.entity.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class NoteService {
    private final NoteServiceImpl impl;
    public List<Note> findAll()                                               { return impl.findAll(); }
    public Note findById(Integer id)                                          { return impl.findById(id); }
    public List<Note> findByCandidatAndMatiere(Integer cId, Integer mId)      { return impl.findByCandidatAndMatiere(cId, mId); }
    public Note create(NoteRequest req)                                       { return impl.create(req); }
    public Note update(Integer id, NoteRequest req)                           { return impl.update(id, req); }
    public void delete(Integer id)                                            { impl.delete(id); }
}
