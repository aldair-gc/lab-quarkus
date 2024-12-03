package infrastructure.repositories;

import infrastructure.repositories.entities.Election;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PanacheElectionRepository implements PanacheRepository<Election> {
}
