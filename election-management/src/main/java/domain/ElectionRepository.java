package domain;

import java.util.List;

public interface ElectionRepository {

    void submit(Election election);

    List<Election> findAll();

    List<Election> findAll(int offset, int limit);

    void delete(String id);

}
