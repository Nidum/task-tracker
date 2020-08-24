package ag.pinguin.repository;

import ag.pinguin.entity.Developer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ensure that JPA is configured correctly
 */
@DataJpaTest
public class DeveloperRepositoryTest {

    @Autowired
    private DeveloperRepository repository;

    @Test
    public void jpaSmokeTest() {
        var ann = new Developer("Ann");
        var id = repository.save(ann).getId();
        var maybeAnn = repository.findById(id);
        assertEquals(ann, maybeAnn.get());
    }
}
