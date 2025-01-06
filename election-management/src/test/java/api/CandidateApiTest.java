package api;

import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import domain.Candidate;
import domain.CandidateService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest

class CandidateApiTest {
    @Inject
    CandidateApi candidateApi;

    @InjectMock
    CandidateService candidateService;

    @Test
    void create() {
        CreateCandidate dto = Instancio.create(CreateCandidate.class);
        ArgumentCaptor<Candidate> captor = ArgumentCaptor.forClass(Candidate.class);
        candidateApi.create(dto);

        verify(candidateService).save(captor.capture());
        verifyNoMoreInteractions(candidateService);

        Candidate candidate = captor.getValue();

        assertEquals(candidate.photo(), dto.photo());
        assertEquals(candidate.givenName(), dto.givenName());
        assertEquals(candidate.familyName(), dto.familyName());
        assertEquals(candidate.email(), dto.email());
        assertEquals(candidate.phone(), dto.phone());
        assertEquals(candidate.jobTitle(), dto.jobTitle());
    }

    @Test
    void update() {
        String id = UUID.randomUUID().toString();
        UpdateCandidate dto = Instancio.create(UpdateCandidate.class);
        Candidate candidate = dto.toDomain(id);

        ArgumentCaptor<Candidate> captor = ArgumentCaptor.forClass(Candidate.class);

        when(candidateService.findById(id)).thenReturn(Uni.createFrom().item(Optional.of(candidate)));

        var response = candidateApi.update(id, dto);

        verify(candidateService).save(captor.capture());
        verify(candidateService).findById(id);
        verifyNoMoreInteractions(candidateService);

        assertEquals(api.dto.out.Candidate.fromDomain(candidate), response.await().indefinitely());
    }

    @Test
    void list() {
        var candidates = Instancio.stream(Candidate.class).limit(10).toList();

        when(candidateService.findAll(0, 10)).thenReturn(Uni.createFrom().item(candidates));

        var response = candidateApi.list(0, 10);

        verify(candidateService).findAll(0, 10);
        verifyNoMoreInteractions(candidateService);

        assertEquals(candidates.stream().map(api.dto.out.Candidate::fromDomain).toList(), response.await().indefinitely());
    }
}
