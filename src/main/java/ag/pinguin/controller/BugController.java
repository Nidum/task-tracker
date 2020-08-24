package ag.pinguin.controller;

import ag.pinguin.dto.request.BugRequest;
import ag.pinguin.dto.response.BugResponse;
import ag.pinguin.entity.Bug;
import ag.pinguin.repository.BugRepository;
import ag.pinguin.repository.DeveloperRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ag.pinguin.exception.Messages.ENTITY_NOT_FOUND;

@Validated
@RestController
@RequestMapping("/api/issue/bug")
public class BugController {

    private final BugRepository repository;
    private final DeveloperRepository developerRepository;

    public BugController(BugRepository repository, DeveloperRepository developerRepository) {
        this.repository = repository;
        this.developerRepository = developerRepository;
    }

    @GetMapping(path = "/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BugResponse> get(@Valid @PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(
                repository.findById(id)
                        .map(this::mapEntityToResponse)
                        .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, id.toString())))
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BugResponse>> getAll() {
        var bugs = repository.findAll();
        var bugsResponse = StreamSupport.stream(bugs.spliterator(), false)
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bugsResponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<BugResponse> create(@Valid @RequestBody BugRequest request) {
        var developer = developerRepository.findById(request.getDeveloperId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ENTITY_NOT_FOUND, request.getDeveloperId().toString())));

        var bug = new Bug(request.getTitle(), request.getDescription(), LocalDate.now(), developer,
                request.getStatus(), request.getPriority());
        return ResponseEntity.ok(mapEntityToResponse(repository.save(bug)));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<BugResponse> update(@Valid @PathVariable @NotNull UUID id,
                                              @Valid @NotNull @RequestBody BugRequest request) {
        var bug = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, id.toString())));
        var developer = developerRepository.findById(request.getDeveloperId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ENTITY_NOT_FOUND, request.getDeveloperId().toString())));
        //TODO: create a mapper
        bug.setDeveloper(developer);
        bug.setTitle(request.getTitle());
        bug.setDescription(request.getDescription());
        bug.setStatus(request.getStatus());
        bug.setPriority(request.getPriority());

        return ResponseEntity.ok(mapEntityToResponse(repository.save(bug)));
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@Valid @PathVariable @NotNull UUID id) {
        repository.deleteById(id);
    }

    // TODO: extract to separate class
    private BugResponse mapEntityToResponse(Bug bug) {
        return new BugResponse(bug.getId(), bug.getTitle(), bug.getDescription(), bug.getCreationDate(),
                bug.getDeveloper().getId(), bug.getStatus(), bug.getPriority());
    }
}
