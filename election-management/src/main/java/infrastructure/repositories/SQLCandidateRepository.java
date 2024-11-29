package infrastructure.repositories;

import domain.CandidateQuery;
import domain.CandidateRepository;
import infrastructure.repositories.entities.Candidate;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class SQLCandidateRepository implements CandidateRepository {

    @Override
    @Transactional
    public void save(List<domain.Candidate> candidates) {
        candidates.stream()
                .map(Candidate::fromDomain)
                .forEach(c -> Candidate.getEntityManager().merge(c).persist());
    }

    @Override
    @Transactional
    public void save(domain.Candidate candidate) {
        Candidate entity = Candidate.fromDomain(candidate);
        Candidate.getEntityManager().merge(entity).persist();
    }

    @Override
    public List<domain.Candidate> find(CandidateQuery query) {
        return Candidate.getEntityManager()
                .createQuery(
                        "select c from candidates c where c.id = ?1 " +
                                "or lower(c.familyName) like lower(?2) " +
                                "or lower(c.givenName) like lower(?2)",
                        Candidate.class
                )
                .setParameter(1, query.ids().orElse(Set.of()))
                .setParameter(2, "%" + query.name().orElse("") + "%")
                .getResultList()
                .stream()
                .map(Candidate::toDomain)
                .toList();
    }

    @Override
    public List<domain.Candidate> findAll() {
        return Candidate.getEntityManager()
                .createQuery("select c from candidates c", Candidate.class)
                .getResultList()
                .stream()
                .map(Candidate::toDomain)
                .toList();
    }

    @Override
    public List<domain.Candidate> findAll(int offset, int size) {
        int page = (int) Math.floor((double) offset / size);
        return Candidate
                .findAll()
                .page(page, size)
                .project(Candidate.class)
                .stream()
                .map(Candidate::toDomain)
                .toList();
    }

    @Override
    public Optional<domain.Candidate> findById(String id) {
        Candidate candidate = Candidate.getEntityManager().find(Candidate.class, id);
        return Optional.ofNullable(candidate).map(Candidate::toDomain);
    }

    @Override
    public void delete(String id) {
        Candidate.deleteById(id);
    }

}
