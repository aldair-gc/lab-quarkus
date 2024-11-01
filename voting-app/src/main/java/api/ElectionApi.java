package api;

import api.dto.out.Election;
import domain.ElectionRepository;
import domain.ElectionService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ElectionApi {
    private final ElectionService service;
    private final ElectionRepository repository;

    public ElectionApi(ElectionService service, ElectionRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    public List<Election> findAll() {
        return service.findAll().stream().map(Election::fromDomain).toList();
    }

    public void vote(String electionId, String candidateId) {
        domain.Election election = repository.findById(electionId);

        election.
                candidates()
                .stream()
                .filter(candidate -> candidate.id()
                        .equals(candidateId))
                        .findFirst()
                        .ifPresent(candidate -> repository.vote(electionId, candidateId));
    }
}
