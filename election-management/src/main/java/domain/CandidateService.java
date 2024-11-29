package domain;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CandidateService {
    private final CandidateRepository repository;

    public CandidateService(CandidateRepository repository) {
        this.repository = repository;
    }

    public void save(Candidate candidate) {
        repository.save(candidate);
    }

    public List<Candidate> findAll() {
        return repository.findAll();
    }

    public List<Candidate> findAll(int page, int size) {
        return repository.findAll(page, size);
    }

    public Candidate findById(String id) {
        return repository.findById(id).orElseThrow();
    }

    public void delete(String id) {
        repository.delete(id);
    }
}