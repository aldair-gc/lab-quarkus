package domain;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import io.smallrye.mutiny.Uni;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@QuarkusTest
class CandidateServiceTest {
    @Inject
    CandidateService service;

    @InjectMock
    CandidateRepository repository;

    @Test
    @RunOnVertxContext
    void save(UniAsserter asserter) {
        Candidate candidate = Instancio.create(Candidate.class);
        asserter.execute(() -> Mockito
            .when(repository.save(candidate))
            .thenReturn(Uni.createFrom().nullItem()));
        asserter.assertNull(() -> service.save(candidate));
        asserter.execute(() -> {
            verify(repository).save(candidate);
            verifyNoMoreInteractions(repository);
        });
        asserter.surroundWith((u -> Panache.withSession(() -> u)));
    }

    @Test
    @RunOnVertxContext
    void findAll(UniAsserter asserter) {
        List<Candidate> candidates = Instancio.stream(Candidate.class).limit(10).toList();
        asserter.execute(() -> Mockito
            .when(repository.findAll())
            .thenReturn(Uni.createFrom().item(candidates)));
        asserter.assertEquals(() -> service.findAll(), candidates);
        asserter.surroundWith((u -> Panache.withSession(() -> u)));
    }

    @Test
    @RunOnVertxContext
    void findById_whenCandidateIsFound_returnsCandidate(UniAsserter asserter) {
        Candidate candidate = Instancio.create(Candidate.class);
        asserter.execute(() -> Mockito
            .when(repository.findById(candidate.id()))
            .thenReturn(Uni.createFrom().item(Optional.of(candidate))));
        asserter.assertEquals(() -> service.findById(candidate.id()), Optional.of(candidate));
        asserter.surroundWith((u -> Panache.withSession(() -> u)));
    }

    @Test
    @RunOnVertxContext
    void findById_whenCandidateIsNotFound_throwsException(UniAsserter asserter) {
        Candidate candidate = Instancio.create(Candidate.class);
        asserter.execute(() -> Mockito
            .when(repository.findById(candidate.id()))
            .thenReturn(Uni.createFrom().item(Optional.empty())));
        asserter.assertFailedWith(() -> service.findById(candidate.id()), NoSuchElementException.class);
        asserter.surroundWith((u -> Panache.withSession(() -> u)));
    }
}
