package api.dto.out;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Optional;

public record Election(String id, List<Candidate> candidates) {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public record Candidate(
            String id,
            Optional<String> photo,
            String fullName,
            String email,
            Optional<String> phone,
            Optional<String> jobTitle,
            Integer votes
    ) {
        public static Candidate fromDomain(domain.Candidate candidate, Integer votes) {
            return new Candidate(
                    candidate.id(),
                    candidate.photo(),
                    candidate.givenName() + " " + candidate.familyName(),
                    candidate.email(),
                    candidate.phone(),
                    candidate.jobTitle(),
                    votes
            );
        }
    }

    public static Election fromDomain(domain.Election election) {
        var candidates = election.votes()
                .entrySet()
                .stream()
                .map(entry -> Candidate.fromDomain(entry.getKey(), entry.getValue()))
                .toList();
        return new Election(election.id(), candidates);
    }
}
