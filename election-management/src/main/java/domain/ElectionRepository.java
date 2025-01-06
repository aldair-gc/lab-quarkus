package domain;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ElectionRepository {

    Uni<Void> submit(Election election);

    Uni<List<Election>> findAll();

    Uni<List<Election>> findAll(int offset, int limit);

    Uni<Void> delete(String id);

}
