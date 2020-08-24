package ag.pinguin.service;

import ag.pinguin.business.PlanningCalculator;
import ag.pinguin.domain.StoryStatus;
import ag.pinguin.dto.response.StoryResponse;
import ag.pinguin.dto.response.WeekPlan;
import ag.pinguin.entity.Developer;
import ag.pinguin.entity.Story;
import ag.pinguin.repository.DeveloperRepository;
import ag.pinguin.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toCollection;

@Service
public class PlanningService {
    private final DeveloperRepository devRepository;
    private final StoryRepository storyRepository;
    private final PlanningCalculator planningCalculator;
    @Value("${developer.maxstorypoints:10}")
    private int maxStoryPoints;

    public PlanningService(DeveloperRepository devRepository, StoryRepository storyRepository,
                           PlanningCalculator planningCalculator) {
        this.devRepository = devRepository;
        this.storyRepository = storyRepository;
        this.planningCalculator = planningCalculator;
    }

    public List<WeekPlan> plan() {
        List<Developer> developers = StreamSupport.stream(devRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        var devCount = developers.size();
        var weeklyStoryPoints = devCount * maxStoryPoints;
        var storyMap = StreamSupport.stream(storyRepository.findAll().spliterator(), false)
                .filter(x -> x.getStatus().equals(StoryStatus.ESTIMATED))
                .filter(x -> x.getEstimate() != null)
                .filter(x -> x.getEstimate() < weeklyStoryPoints)
                .collect(Collectors.groupingBy(Story::getEstimate, toCollection(LinkedList::new)));

        List<WeekPlan> result = new ArrayList<>();
        int week = 1;

        while (storyMap.values().stream().flatMap(Collection::stream).findAny().isPresent()) {
            Map<Integer, Integer> estimateToStoryCountMap = storyMap.entrySet()
                    .stream()
                    .filter(x->!x.getValue().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().size()));
            List<Integer> weekPlan = planningCalculator.planWeek(estimateToStoryCountMap, weeklyStoryPoints);
            List<StoryResponse> stories = weekPlan.stream()
                    .map(est -> storyMap.get(est).pop())
                    .map(x -> new StoryResponse(x.getId(), x.getTitle(), x.getDescription(),
                            x.getCreationDate(), x.getDeveloper() == null ? null : x.getDeveloper().getId(),
                            x.getEstimate(), x.getStatus()))
                    .collect(Collectors.toList());
            int totalStoryPoints = weekPlan.stream().mapToInt(x -> x).sum();
            result.add(new WeekPlan(week++, stories, totalStoryPoints));
        }

        return result;
    }

}
