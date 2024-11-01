package api;

import domain.Candidate;
import domain.CandidateService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CandidateApi {

    private final CandidateService service;

    public CandidateApi(CandidateService service) {
        this.service = service;
    }

    public void create(api.dto.in.CreateCandidate dto) {
        service.save(dto.toDomain());
    }

    public api.dto.out.Candidate update(String id, api.dto.in.UpdateCandidate dto) {
        service.save(dto.toDomain(id));

        Candidate candidate = service.findById(id);

        return api.dto.out.Candidate.fromDomain(candidate);
    }

    public List<api.dto.out.Candidate> list() {
        List<Candidate> candidates = service.findAll();

        return candidates.stream().map(api.dto.out.Candidate::fromDomain).toList();
    }
}
