package ag.pinguin.repository;

import ag.pinguin.entity.Developer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeveloperRepository extends CrudRepository<Developer, UUID> {
}
