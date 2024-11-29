package domain;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository {

    void save(List<Candidate> candidates);

    default void save(Candidate candidate) {
        save(List.of(candidate));
    }

    List<Candidate> find(CandidateQuery query);

    List<Candidate> findAll();

    List<Candidate> findAll(int offset, int size);

    Optional<Candidate> findById(String id);

    void delete(String id);

}
