package infrastructure.repositories.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ElectionCandidateId implements Serializable {

    @Column(name = "election_id")
    public String electionId;

    @Column(name = "candidate_id")
    public String candidateId;

}