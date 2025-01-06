package domain;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CandidateService {
    private final CandidateRepository repository;

    @Inject
    public CandidateService(CandidateRepository repository) {
        this.repository = repository;
    }

    public Uni<Void> save(Candidate candidate) {
        return repository.save(candidate);
    }

    public Uni<Void> save(List<Candidate> candidates) {
        return repository.save(candidates);
    }

    public Uni<List<Candidate>> findAll() {
        return repository.findAll();
    }

    public Uni<List<Candidate>> findAll(int page, int size) {
        return repository.findAll(page, size);
    }

    public Uni<Optional<Candidate>> findById(String id) {
        return repository.findById(id).invoke(Optional::orElseThrow);
    }

    public Uni<Void> delete(String id) {
        return repository.delete(id);
    }
}