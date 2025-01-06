package domain;

import domain.annotations.Principal;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ElectionService {
    private final CandidateService candidateService;
    private final Instance<ElectionRepository> repositories;
    private final ElectionRepository repository;

    @Inject
    public ElectionService(CandidateService candidateService,
                           @Any Instance<ElectionRepository> repositories,
                           @Principal ElectionRepository repository) {
        this.candidateService = candidateService;
        this.repositories = repositories;
        this.repository = repository;
    }

    public Uni<List<Election>> findAll() {
        return repository.findAll();
    }

    public Uni<Election> submit() {
        Uni<Election> election = candidateService.findAll().map(Election::create);
        return election.invoke(e -> repositories.forEach(repository -> repository.submit(e)));
    }

    public Uni<Void> delete(String id) {
        return repository.delete(id);
    }
}