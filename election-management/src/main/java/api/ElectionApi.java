package api;

import api.dto.out.Election;
import domain.ElectionService;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ElectionApi {

    private final ElectionService service;

    public ElectionApi(ElectionService service) {
        this.service = service;
    }

    public Uni<Void> submit() {
        service.submit();
        return null;
    }

    public Uni<List<Election>> findAll() {
        return service.findAll()
            .map(elections -> elections.stream()
                .map(Election::fromDomain)
                .toList());

    }

    public Uni<Void> delete(String id) {
        return service.delete(id);
    }
}
