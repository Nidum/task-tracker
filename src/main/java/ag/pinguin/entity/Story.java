package ag.pinguin.entity;

import ag.pinguin.domain.StoryStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "STORY")
public class Story extends Issue {
    @Positive
    private Integer estimate;
    @Enumerated(value = EnumType.STRING)
    private StoryStatus status = StoryStatus.NEW;

    public Story() {
    }

    public Story(String title, String description, LocalDate creationDate,
                 Developer developer, int estimate, StoryStatus status) {
        super(title, description, creationDate, developer);
        this.estimate = estimate;
        this.status = status;
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public StoryStatus getStatus() {
        return status;
    }

    public void setStatus(StoryStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Story story = (Story) o;
        return estimate == story.estimate &&
                status == story.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), estimate, status);
    }
}
