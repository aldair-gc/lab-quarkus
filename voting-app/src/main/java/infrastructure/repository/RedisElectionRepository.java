package infrastructure.repository;

import domain.Candidate;
import domain.Election;
import domain.ElectionRepository;
import io.quarkus.cache.CacheResult;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.sortedset.SortedSetCommands;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class RedisElectionRepository implements ElectionRepository {

    private static final Logger LOGGER = Logger.getLogger(RedisElectionRepository.class);
    private final SortedSetCommands<String, String> sortedSetCommands;
    private final KeyCommands<String> keyCommands;

    public RedisElectionRepository(RedisDataSource redisDataSource) {
        sortedSetCommands = redisDataSource.sortedSet(String.class, String.class);
        keyCommands = redisDataSource.key(String.class);
    }

    @Override
    @CacheResult(cacheName = "memoization")
    public Election findById(String id) {
        LOGGER.info("Retrieving election " + id + " from Redis");

        List<Candidate> candidates = sortedSetCommands
                .zrange("election:" + id, 0, -1)
                .stream()
                .map(Candidate::new)
                .toList();

        return new Election(id, candidates);
    }

    @Override
    public List<Election> findAll() {
        LOGGER.info("Retrieving all elections from Redis");
        return keyCommands.keys("election:*")
                .stream()
                .map(id -> findById(id.replace("elections:", "")))
                .toList();
    }
}
