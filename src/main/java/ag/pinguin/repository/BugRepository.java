package ag.pinguin.repository;

import ag.pinguin.entity.Bug;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BugRepository extends CrudRepository<Bug, UUID> {
    @Query(value = "SELECT b FROM BUG b LEFT JOIN FETCH b.developer WHERE b.id = :id")
    Optional<Bug> findById(@Param("id") UUID id);
}
