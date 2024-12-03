package infrastructure.repositories;

import infrastructure.repositories.entities.Candidate;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PanacheCandidateRepository implements PanacheRepository<Candidate> {
    public List<Candidate> findByName(String name) {
        return list(
            "lower(givenName) like lower(?1) or lower(familyName) like lower(?1)"
            , "%" + name + "%"
        );
    }

    public List<Candidate> findAllWithPagination(int offset, int size) {
        return find("select c from candidates c")
            .page(offset, size)
            .list();
    }
}
