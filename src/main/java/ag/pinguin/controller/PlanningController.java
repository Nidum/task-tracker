package ag.pinguin.controller;

import ag.pinguin.dto.response.WeekPlan;
import ag.pinguin.service.PlanningService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/planning")
public class PlanningController {

    private final PlanningService service;

    public PlanningController(PlanningService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WeekPlan>> get() {
        return ResponseEntity.ok(service.plan());
    }
}
