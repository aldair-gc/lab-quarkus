package api;

import domain.Candidate;
import domain.CandidateService;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CandidateApi {

    private final CandidateService service;

    public CandidateApi(CandidateService service) {
        this.service = service;
    }

    public Uni<Void> create(api.dto.in.CreateCandidate dto) {
        return service.save(dto.toDomain());
    }

    public Uni<api.dto.out.Candidate> update(String id, api.dto.in.UpdateCandidate dto) {
        service.save(dto.toDomain(id));
        return service.findById(id)
            .onItem()
            .transform(c -> api.dto.out.Candidate.fromDomain(c.orElseThrow()));
    }

    public Uni<List<api.dto.out.Candidate>> list(int page, int size) {
        Uni<List<Candidate>> candidates = service.findAll(page, size);
        return candidates.onItem().transform(list -> list
            .stream()
            .map(api.dto.out.Candidate::fromDomain)
            .toList());
    }

    public Uni<Void> delete(String id) {
        return service.delete(id);
    }

}
