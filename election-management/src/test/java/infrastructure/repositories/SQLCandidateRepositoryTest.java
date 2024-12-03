package infrastructure.repositories;

import domain.CandidateRepository;
import domain.CandidateRepositoryTest;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;

@QuarkusTest
class SQLCandidateRepositoryTest extends CandidateRepositoryTest {

    @Inject
    PanacheCandidateRepository panacheCandidateRepository;

    @Inject
    SQLCandidateRepository candidateRepository;

    @Override
    public CandidateRepository repository() {
        return candidateRepository;
    }

    @BeforeEach
    @TestTransaction
    void tearDown() {
        panacheCandidateRepository
            .getEntityManager()
            .createNativeQuery("TRUNCATE TABLE candidates")
            .executeUpdate();
    }

}