package ag.pinguin.business;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PlanningCalculator {
    private static final int INFINITY = Integer.MAX_VALUE - 1;

    public List<Integer> planWeek(Map<Integer, Integer> estimateToStoryCountMap, int totalStoryPoints) {
        List<Integer> possibleEstimates = new ArrayList<>(estimateToStoryCountMap.keySet());
        possibleEstimates.sort(Comparator.naturalOrder());

        int[] partialSums = new int[totalStoryPoints + 1];
        Map<Integer, Map<Integer, Integer>> partialSumToEstimateMap = new HashMap<>();
        partialSumToEstimateMap.put(0, new HashMap<>());

        for (int i = 1; i < partialSums.length; i++) {
            partialSums[i] = INFINITY;
            partialSumToEstimateMap.put(i, new HashMap<>());
            for (Integer estimate : possibleEstimates) {
                if (estimate <= i &&
                        (partialSums[i] - 1 > partialSums[i - estimate] ||
                                getSum(partialSumToEstimateMap.get(i)) <
                                        getSum(partialSumToEstimateMap.get(i - estimate)) + estimate)) {
                    partialSumToEstimateMap.get(i).putIfAbsent(estimate, 0);
                    Integer tasksCountForEstimate =
                            partialSumToEstimateMap.get(i - estimate).getOrDefault(estimate, 0);

                    if (tasksCountForEstimate < estimateToStoryCountMap.get(estimate)) {
                        // Overwrite all current values with more optimal
                        partialSumToEstimateMap.put(i, new HashMap<>(partialSumToEstimateMap.get(i - estimate)));
                        partialSumToEstimateMap.get(i).put(estimate, tasksCountForEstimate + 1);
                        partialSums[i] = partialSums[i - estimate] + 1;
                    } else if (!hasTasksAssigned(partialSumToEstimateMap.get(i))) {
                        partialSumToEstimateMap.put(i, new HashMap<>(partialSumToEstimateMap.get(i - estimate)));
                        partialSums[i] = partialSums[i - estimate];
                    }
                } else if (estimate <= i && getSum(partialSumToEstimateMap.get(i)) == 0) {
                    partialSumToEstimateMap.get(i).putIfAbsent(estimate, 1);
                    partialSums[i] = 1;
                }
            }
        }

        return partialSumToEstimateMap.get(totalStoryPoints).entrySet()
                .stream()
                .flatMap(x -> Stream.generate(x::getKey).limit(x.getValue()))
                .collect(Collectors.toList());
    }

    private int getSum(Map<Integer, Integer> estimateToTaskCount) {
        return estimateToTaskCount.entrySet().stream()
                .map(x -> x.getKey() * x.getValue())
                .mapToInt(x -> x)
                .sum();
    }

    private boolean hasTasksAssigned(Map<Integer, Integer> estimateToTaskCount) {
        return estimateToTaskCount.values().stream().allMatch(x -> x != 0);
    }
}
