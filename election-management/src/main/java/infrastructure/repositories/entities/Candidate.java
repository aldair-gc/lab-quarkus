package infrastructure.repositories.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity(name="candidates")
public class Candidate extends PanacheEntityBase {

    @Id
    private String id;
    private String photo;
    @Column(name="given_name")
    private String givenName;
    @Column(name="family_name")
    private String familyName;
    private String email;
    private String phone;
    @Column(name="job_title")
    private String jobTitle;

    public static Candidate fromDomain(domain.Candidate domain) {
        Candidate entity = new Candidate();

        entity.id = domain.id();
        entity.photo = domain.photo().orElse(null);
        entity.givenName = domain.givenName();
        entity.familyName = domain.familyName();
        entity.email = domain.email();
        entity.phone = domain.phone().orElse(null);
        entity.jobTitle = domain.jobTitle().orElse(null);

        return entity;
    }

    public static domain.Candidate toDomain(Candidate entity) {
        return new domain.Candidate(
                entity.id,
                Optional.ofNullable(entity.photo),
                entity.givenName,
                entity.familyName,
                entity.email,
                Optional.ofNullable(entity.phone),
                Optional.ofNullable(entity.jobTitle)
        );
    }

    public static Uni<List<Candidate>> findByName(String name) {
        return list(
            "lower(givenName) like lower(?1) or lower(familyName) like lower(?1)"
            , "%" + name + "%"
        );
    }

    public static Uni<List<Candidate>> findAllWithPagination(int offset, int size) {
        return find("select c from candidates c")
            .page(offset, size)
            .list();
    }

}
