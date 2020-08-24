package ag.pinguin.business;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanningCalculatorTest {

    private final PlanningCalculator calculator = new PlanningCalculator();

    @ParameterizedTest
    @MethodSource
    public void validateCalculations(Map<Integer, Integer> estimateToStoryCountMap, int totalStoryPoints, List<Integer> estimates) {
        List<Integer> result = calculator.planWeek(new HashMap<>(estimateToStoryCountMap), totalStoryPoints);
        result.sort(Comparator.naturalOrder());
        assertEquals(estimates, result);
    }

    @MethodSource
    public static Stream<Arguments> validateCalculations() {
        return Stream.of(
                 Arguments.of(Map.of(6, 1, 5, 2, 3, 1, 2, 1), 10, List.of(5, 5)),
                 Arguments.of(Map.of(3, 1, 2, 1), 6, List.of(2, 3)),
                 Arguments.of(Map.of(6, 2, 5, 1, 4,1), 4, List.of(4)),
                 Arguments.of(Map.of(5, 1, 3, 1, 2, 2), 10, List.of(2, 3, 5)),
                 Arguments.of(Map.of(7, 2, 5, 2, 4,1), 10, List.of(5, 5)),
                 Arguments.of(Map.of(1, 10), 10, List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1)),
                 Arguments.of(Map.of(2, 4, 1, 1), 10, List.of(1, 2, 2, 2, 2)),
                 Arguments.of(Map.of(11,1), 10, List.of()),
                 Arguments.of(Map.of(5, 1), 10, List.of(5)),
                 Arguments.of(Map.of(8, 1, 9, 1), 10, List.of(9))
        );
    }
}
