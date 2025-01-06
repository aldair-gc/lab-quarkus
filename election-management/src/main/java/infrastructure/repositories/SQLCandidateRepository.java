package infrastructure.repositories;

import domain.CandidateQuery;
import domain.CandidateRepository;
import infrastructure.repositories.entities.Candidate;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SQLCandidateRepository implements CandidateRepository {

    @Override
    @WithTransaction
    public Uni<Void> save(domain.Candidate candidate) {
        return Candidate.persist(Candidate.fromDomain(candidate));
    }

    @Override
    @WithTransaction
    public Uni<Void> save(List<domain.Candidate> candidates) {
        return Candidate.persist(candidates.stream().map(Candidate::fromDomain));
    }

    @Override
    @WithSession
    public Uni<List<domain.Candidate>> find(CandidateQuery query) {
        return Candidate
            .find("id, name", query.ids(), query.name())
            .list()
            .map(l -> l
                .stream()
                .map(c -> Candidate.toDomain((Candidate) c))
                .toList()
            );
    }

    @Override
    @WithSession
    public Uni<List<domain.Candidate>> findAll() {
        return Candidate
            .listAll()
            .map(l -> l
                .stream()
                .map(c -> Candidate.toDomain((Candidate) c))
                .toList()
            );
    }

    @Override
    @WithSession
    public Uni<List<domain.Candidate>> findAll(int offset, int size) {
        return Candidate
            .listAll()
            .map(l -> l
                .stream()
                .skip(offset)
                .limit(size)
                .map(c -> Candidate.toDomain((Candidate) c))
                .toList()
            );
    }

    @Override
    @WithSession
    public Uni<Optional<domain.Candidate>> findById(String id) {
        return Candidate
            .findById(id)
            .map(Optional::ofNullable)
            .map(c -> c
                .map(ca -> Candidate.toDomain((Candidate) ca))
            );
    }

    @Override
    @WithSession
    public Uni<Optional<domain.Candidate>> findByName(String name) {
        return Candidate
            .findByName(name)
            .map(l -> l
                .stream()
                .map(c -> Candidate.toDomain((Candidate) c))
                .findFirst()
            );
    }

    @Override
    @WithTransaction
    public Uni<Void> delete(String id) {
        return Candidate
            .delete("id", id)
            .map(v -> null);
    }

}
