package infrastructure.repositories;

import infrastructure.repositories.entities.ElectionCandidate;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PanacheElectionCandidateRepository implements PanacheRepository<ElectionCandidate> {
}
