package ag.pinguin.repository;

import ag.pinguin.entity.Story;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StoryRepository extends CrudRepository<Story, UUID> {
    @Query(value = "SELECT s FROM STORY s LEFT JOIN FETCH s.developer WHERE s.id = :id")
    Optional<Story> findById(@Param("id") UUID id);
}
