package ag.pinguin.dto.response;

import java.util.List;
import java.util.Objects;

public class WeekPlan {
    private int week;
    private List<StoryResponse> stories;
    private int totalStoryPoints;

    public WeekPlan() {
    }

    public WeekPlan(int week, List<StoryResponse> stories, int totalStoryPoints) {
        this.week = week;
        this.stories = stories;
        this.totalStoryPoints = totalStoryPoints;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public List<StoryResponse> getStories() {
        return stories;
    }

    public void setStories(List<StoryResponse> stories) {
        this.stories = stories;
    }

    public int getTotalStoryPoints() {
        return totalStoryPoints;
    }

    public void setTotalStoryPoints(int totalStoryPoints) {
        this.totalStoryPoints = totalStoryPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeekPlan weekPlan = (WeekPlan) o;
        return week == weekPlan.week &&
                totalStoryPoints == weekPlan.totalStoryPoints &&
                Objects.equals(stories, weekPlan.stories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(week, stories, totalStoryPoints);
    }
}
