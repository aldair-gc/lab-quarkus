package infrastructure.repositories.entities;


import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity(name = "election_candidate")
public class ElectionCandidate extends PanacheEntityBase {

    @Id
    @EmbeddedId
    public ElectionCandidateId id;
    public Integer votes;

    public static ElectionCandidate fromDomain(
            domain.Election election,
            domain.Candidate candidate,
            Integer votes
    ) {
        ElectionCandidate entity = new ElectionCandidate();

        ElectionCandidateId id = new ElectionCandidateId();
        id.electionId = election.id();
        id.candidateId = candidate.id();

        entity.id = id;
        entity.votes = votes;

        return entity;
    }

}
