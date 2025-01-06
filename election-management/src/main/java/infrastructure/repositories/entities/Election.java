package infrastructure.repositories.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.*;

@Entity(name = "elections")
public class Election extends PanacheEntityBase {

    @Id
    private String id;

    public static Election fromDomain(domain.Election domain) {
        Election entity = new Election();
        entity.id = domain.id();
        return entity;
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
