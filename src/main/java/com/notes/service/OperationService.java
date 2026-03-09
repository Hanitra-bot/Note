package com.notes.service;
import com.notes.dto.OperationRequest;
import com.notes.entity.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class OperationService {
    private final OperationServiceImpl impl;
    public List<Operation> findAll()                             { return impl.findAll(); }
    public Operation findById(Integer id)                        { return impl.findById(id); }
    public Operation create(OperationRequest req)                { return impl.create(req); }
    public Operation update(Integer id, OperationRequest req)    { return impl.update(id, req); }
    public void delete(Integer id)                               { impl.delete(id); }
}
