package infrastructure.repositories.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "election_candidate")
public class ElectionCandidate {

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
