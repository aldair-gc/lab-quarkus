package infrastructure.repositories;

import domain.ElectionRepository;
import domain.annotations.Principal;
import infrastructure.repositories.entities.Election;
import infrastructure.repositories.entities.ElectionCandidate;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Principal
@ApplicationScoped
public class SQLElectionRepository implements ElectionRepository {

    private final PanacheElectionRepository electionRepository;
    private final PanacheElectionCandidateRepository electionCandidateRepository;

    @Inject
    public SQLElectionRepository(PanacheElectionRepository electionRepository, PanacheElectionCandidateRepository electionCandidateRepository) {
        this.electionRepository = electionRepository;
        this.electionCandidateRepository = electionCandidateRepository;
    }

    @Transactional
    public void submit(domain.Election election) {
        Election entity = Election.fromDomain(election);

        electionRepository.persist(entity);

        election
            .votes()
            .entrySet()
            .stream()
            .map(entry -> ElectionCandidate
                .fromDomain(election, entry.getKey(), entry.getValue()))
            .forEach(electionCandidateRepository::persist);
    }

    public List<domain.Election> findAll() {
        return List.of();
    }

    @RegisterForReflection
    public static class ElectionCandidateResult {
        public String election_id;
        public String candidate_id;
        public String photo;
        public String given_name;
        public String family_name;
        public String email;
        public String phone;
        public String job_title;
        public Integer votes;
    }

    public List<domain.Election> findAll(int offset, int limit) {
        String electionQuery = "SELECT " + "e.id AS election_id, "
            + "c.id AS candidate_id, " + "c.photo, " + "c.given_name, "
            + "c.family_name, " + "c.email, " + "c.phone, " + "c.job_title, "
            + "ec.votes " + "FROM elections AS e "
            + "INNER JOIN election_candidate AS ec ON ec.election_id = e.id "
            + "INNER JOIN candidates AS c ON c.id = ec.candidate_id";

        int page = offset / limit;

        Stream<ElectionCandidateResult> stream = electionCandidateRepository
            .find(electionQuery)
            .page(page, limit)
            .project(ElectionCandidateResult.class)
            .stream();

        Map<String, List<ElectionCandidateResult>> grouped = stream
            .collect(Collectors.groupingBy(ecr -> ecr.election_id));

        return grouped
            .entrySet()
            .stream()
            .map(group -> Map.entry(
                    group.getKey(),
                    group.getValue()
                        .stream()
                        .map(electionCandidate -> Map.entry(
                            new domain.Candidate(
                                electionCandidate.candidate_id,
                                Optional.ofNullable(electionCandidate.photo),
                                electionCandidate.given_name,
                                electionCandidate.family_name,
                                electionCandidate.email,
                                Optional.ofNullable(electionCandidate.phone),
                                Optional.ofNullable(electionCandidate.job_title)
                            ),
                            electionCandidate.votes
                        ))
                )
            )
            .map(entry -> new domain.Election(
                    entry.getKey(),
                    entry.getValue().collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                )
            )
            .collect(Collectors.toList());
    }

    public void delete(String id) {
        electionRepository.delete("id", id);
    }

    @Transactional
    public void sync(domain.Election election) {
        election
            .votes()
            .entrySet()
            .stream()
            .map(entry -> ElectionCandidate
                .fromDomain(election, entry.getKey(), entry.getValue())
            )
            .forEach(electionCandidateRepository::persist);
    }
}
