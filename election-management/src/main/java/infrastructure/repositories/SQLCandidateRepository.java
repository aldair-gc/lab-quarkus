package infrastructure.repositories;

import domain.CandidateQuery;
import domain.CandidateRepository;
import infrastructure.repositories.entities.Candidate;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SQLCandidateRepository implements CandidateRepository {

    private final PanacheCandidateRepository repository;

    @Inject
    public SQLCandidateRepository(PanacheCandidateRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void save(List<domain.Candidate> candidates) {
        candidates.stream()
            .map(Candidate::fromDomain)
            .forEach(candidate -> repository.getEntityManager().merge(candidate));
    }

    @Transactional
    public void save(domain.Candidate candidate) {
        Candidate entity = Candidate.fromDomain(candidate);
        repository.getEntityManager().merge(entity);
    }

    public List<domain.Candidate> find(CandidateQuery query) {
        return repository.findByName(query.name().orElse("")).stream().map(Candidate::toDomain).toList();
    }

    public List<domain.Candidate> findAll() {
        return repository.streamAll().map(Candidate::toDomain).toList();
    }

    public List<domain.Candidate> findAll(int offset, int size) {
        int page = (int) Math.floor((double) offset / size);
        return repository.findAllWithPagination(page, size).stream().map(Candidate::toDomain).toList();
    }

    public Optional<domain.Candidate> findById(String id) {
        Optional<Candidate> candidate = repository.find("id", id).stream().findFirst();
        return candidate.map(Candidate::toDomain);
    }

    public void delete(String id) {
        repository.delete("id", id);
    }

}
