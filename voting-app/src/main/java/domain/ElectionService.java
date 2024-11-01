package domain;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ElectionService {
    private final ElectionRepository repository;

    public ElectionService(ElectionRepository repository) {
        this.repository = repository;
    }

    public List<Election> findAll() {
        return repository.findAll();
    }
}
