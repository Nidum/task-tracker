package ag.pinguin.controller;

import ag.pinguin.dto.DeveloperRequest;
import ag.pinguin.dto.DeveloperResponse;
import ag.pinguin.entity.Developer;
import ag.pinguin.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static ag.pinguin.exception.Messages.ENTITY_NOT_FOUND;

@Validated
@RestController
@RequestMapping("/api/developer")
public class DeveloperController {

    private final DeveloperRepository repository;

    @Autowired
    public DeveloperController(DeveloperRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeveloperResponse> get(@Valid @PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(
                repository.findById(id)
                        .map(this::mapEntityToResponse)
                        .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, id.toString())))
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeveloperResponse>> getAll() {
        var developers = repository.findAll();
        var developersReponse = StreamSupport.stream(developers.spliterator(), false)
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(developersReponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeveloperResponse> create(@Valid @RequestBody DeveloperRequest request) {
        var developer = new Developer(request.getFirstName());
        return ResponseEntity.ok(
                mapEntityToResponse(repository.save(developer))
        );
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<DeveloperResponse> update(@Valid @PathVariable @NotNull UUID id,
                                                    @Valid @NotNull @RequestBody DeveloperRequest request) {
        var developer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, id.toString())));
        developer.setFirstName(request.getFirstName());
        return ResponseEntity.ok(mapEntityToResponse(repository.save(developer)));
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@Valid @PathVariable @NotNull UUID id) {
        repository.deleteById(id);
    }

    // TODO: extract to separate class
    private DeveloperResponse mapEntityToResponse(Developer developer) {
        return new DeveloperResponse(developer.getId(), developer.getFirstName());
    }

}
