package infrastructure.repositories;

import infrastructure.repositories.entities.Candidate;
import io.quarkus.test.junit.QuarkusTest;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PanacheCandidateRepositoryTest {

    @Inject
    PanacheCandidateRepository panacheRepository;

    @Test
    @Transactional
    void findByNameReturnsMatchingCandidates() {
        Candidate candidate = Instancio.create(Candidate.class);
        panacheRepository.persist(candidate);
        List<Candidate> found = panacheRepository.findByName(candidate.getGivenName());
        assertEquals(1, found.size());
        assertEquals(candidate.getGivenName(), found.get(0).getGivenName());
    }

    @Test
    void findByNameReturnsEmptyListWhenNoMatch() {
        List<Candidate> found = panacheRepository.findByName("NonExistentName");
        assertTrue(found.isEmpty());
    }

    @Test
    @Transactional
    void findAllWithPaginationReturnsCorrectPage() {
        List<Candidate> candidates = List.of(
            Instancio.create(Candidate.class),
            Instancio.create(Candidate.class),
            Instancio.create(Candidate.class)
        );
        panacheRepository.persist(candidates);
        List<Candidate> found = panacheRepository.findAllWithPagination(0, 2);
        assertEquals(2, found.size());
    }

    @Test
    void findAllWithPaginationReturnsEmptyListWhenOutOfBounds() {
        List<Candidate> found = panacheRepository.findAllWithPagination(1000, 2);
        assertTrue(found.isEmpty());
    }
}