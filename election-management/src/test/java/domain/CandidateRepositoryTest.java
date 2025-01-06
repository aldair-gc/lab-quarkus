package domain;

import io.quarkus.test.hibernate.reactive.panache.TransactionalUniAsserter;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.instancio.Select.field;

@QuarkusTest
public abstract class CandidateRepositoryTest {
    public abstract CandidateRepository repository();

    @Test
    @RunOnVertxContext
    void save(TransactionalUniAsserter asserter) {
        Candidate candidate = Instancio.create(Candidate.class);
        asserter.execute(() -> repository().save(candidate));
        asserter.assertEquals(() -> repository().findById(candidate.id()), Optional.of(candidate));
        asserter.execute(() -> repository().delete(candidate.id()));
    }

    @Test
    @RunOnVertxContext
    void findAll(TransactionalUniAsserter asserter) {
        List<Candidate> candidates = Instancio.stream(Candidate.class).limit(10).toList();
        asserter.execute(() -> repository().save(candidates));
        asserter.assertTrue(() -> repository().findAll().map(lc -> lc.containsAll(candidates)));
        asserter.execute(() -> repository().delete(candidates.get(0).id()));
        asserter.execute(() -> candidates.forEach(c -> repository().delete(c.id())));
    }

    @Test
    @RunOnVertxContext
    void findByName(TransactionalUniAsserter asserter) {
        Candidate candidate1 = Instancio.create(Candidate.class);
        Candidate candidate2 = Instancio.of(Candidate.class)
            .set(field("familyName"), "Donut")
            .create();
        asserter.execute(() -> repository().save(List.of(candidate1, candidate2)));
        asserter.assertEquals(() -> repository().findByName(candidate1.familyName()), Optional.of(candidate1));
        asserter.execute(() -> repository().delete(candidate1.id()));
        asserter.execute(() -> repository().delete(candidate2.id()));
    }

}
