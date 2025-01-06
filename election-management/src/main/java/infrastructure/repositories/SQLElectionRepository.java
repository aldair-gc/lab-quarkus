package infrastructure.repositories;

import domain.Candidate;
import domain.ElectionRepository;
import domain.annotations.Principal;
import infrastructure.repositories.entities.Election;
import infrastructure.repositories.entities.ElectionCandidate;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.panache.common.Page;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Principal
@ApplicationScoped
public class SQLElectionRepository implements ElectionRepository {

    @WithTransaction
    public Uni<Void> submit(domain.Election election) {
        Election entity = Election.fromDomain(election);

        Election.persist(entity);

        election
            .votes()
            .entrySet()
            .stream()
            .map(entry -> ElectionCandidate
                .fromDomain(election, entry.getKey(), entry.getValue()))
            .forEach(ec -> ec.persist());

        return Uni.createFrom().voidItem();
    }

    @RegisterForReflection
    public static class ElectionCandidateDto {
        public Integer votes;
        public String electionId;
        public String candidateId;
        public String photo;
        public String givenName;
        public String familyName;
        public String email;
        public String phone;
        public String jobTitle;

        public ElectionCandidateDto(
            Integer votes,
            @ProjectedFieldName("id.electionId") String electionId,
            @ProjectedFieldName("id.candidateId") String candidateId,
            @ProjectedFieldName("candidate.photo") String photo,
            @ProjectedFieldName("candidate.given_name") String givenName,
            @ProjectedFieldName("candidate.family_name") String familyName,
            @ProjectedFieldName("candidate.email") String email,
            @ProjectedFieldName("candidate.phone") String phone,
            @ProjectedFieldName("candidate.job_title") String jobTitle
        ) {
            this.votes = votes;
            this.electionId = electionId;
            this.candidateId = candidateId;
            this.photo = photo;
            this.givenName = givenName;
            this.familyName = familyName;
            this.email = email;
            this.phone = phone;
            this.jobTitle = jobTitle;
        }
    }

    @WithSession
    public Uni<List<domain.Election>> findAll() {
        PanacheQuery<ElectionCandidateDto> query = ElectionCandidate
            .findAll()
            .project(ElectionCandidateDto.class);

        Stream<ElectionCandidateDto> stream = ElectionCandidate
            .findAll()
            .project(ElectionCandidateDto.class)
            .list()
            .await()
            .indefinitely()
            .stream();

        Map<String, List<ElectionCandidateDto>> grouped = stream
            .collect(Collectors.groupingBy(ecr -> ecr.electionId));

        List<domain.Election> collect = grouped
            .entrySet()
            .stream()
            .map(group -> Map.entry(
                    group.getKey(),
                    group.getValue()
                        .stream()
                        .map(electionCandidate -> Map.entry(
                            new Candidate(
                                electionCandidate.candidateId,
                                Optional.ofNullable(electionCandidate.photo),
                                electionCandidate.givenName,
                                electionCandidate.familyName,
                                electionCandidate.email,
                                Optional.ofNullable(electionCandidate.phone),
                                Optional.ofNullable(electionCandidate.jobTitle)
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

        return Uni.createFrom().item(collect);
    }

    @WithSession
    public Uni<List<domain.Election>> findAll(int offset, int limit) {
        PanacheQuery<ElectionCandidateDto> query = ElectionCandidate
            .findAll()
            .project(ElectionCandidateDto.class);

        int page = offset / limit;

        Stream<ElectionCandidateDto> stream = ElectionCandidate
            .findAll()
            .page(Page.of(page, limit))
            .project(ElectionCandidateDto.class)
            .list()
            .await()
            .indefinitely()
            .stream();

        Map<String, List<ElectionCandidateDto>> grouped = stream
            .collect(Collectors.groupingBy(ecr -> ecr.electionId));

        List<domain.Election> collect = grouped
            .entrySet()
            .stream()
            .map(group -> Map.entry(
                    group.getKey(),
                    group.getValue()
                        .stream()
                        .map(electionCandidate -> Map.entry(
                            new Candidate(
                                electionCandidate.candidateId,
                                Optional.ofNullable(electionCandidate.photo),
                                electionCandidate.givenName,
                                electionCandidate.familyName,
                                electionCandidate.email,
                                Optional.ofNullable(electionCandidate.phone),
                                Optional.ofNullable(electionCandidate.jobTitle)
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

        return Uni.createFrom().item(collect);
    }

    public Uni<Void> delete(String id) {
        return ElectionCandidate.delete("id", id).replaceWithVoid();
    }

    @WithSession
    public Uni<Void> sync(domain.Election election) {
        election
            .votes()
            .entrySet()
            .stream()
            .map(entry -> ElectionCandidate
                .fromDomain(election, entry.getKey(), entry.getValue())
            )
            .forEach(ec -> ec.persist());

        return Uni.createFrom().voidItem();
    }
}
