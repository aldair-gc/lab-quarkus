package infrastructure.repositories;

import domain.CandidateRepository;
import domain.CandidateRepositoryTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
class SQLCandidateRepositoryTest extends CandidateRepositoryTest {

    @Inject
    SQLCandidateRepository repository;

    @Override
    public CandidateRepository repository() {
        return repository;
    }

//    @BeforeEach
//    @RunOnVertxContext
//    void tearDown(TransactionalUniAsserter asserter) {
//        asserter.execute(() -> repository.findAll().invoke(candidates -> candidates.forEach(candidate -> repository.delete(candidate.id()))));
//    }

}