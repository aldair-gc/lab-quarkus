package domain;

import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository {

    Uni<Void> save(List<Candidate> candidates);

    default Uni<Void> save(Candidate candidate) {
        return save(List.of(candidate));
    }

    Uni<List<Candidate>> find(CandidateQuery query);

    Uni<List<Candidate>> findAll();

    Uni<List<Candidate>> findAll(int offset, int size);

    Uni<Optional<Candidate>> findById(String id);

    Uni<Optional<Candidate>> findByName(String name);

    Uni<Void> delete(String id);

}
