package ag.pinguin.dto.response;

import ag.pinguin.domain.StoryStatus;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class StoryResponse extends IssueResponse {
    private int estimate;
    private StoryStatus status;

    public StoryResponse() {
    }

    public StoryResponse(UUID id, String title, String description, LocalDate creationDate,
                         UUID developerId, int estimate, StoryStatus status) {
        super(id, title, description, creationDate, developerId);
        this.estimate = estimate;
        this.status = status;
    }

    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
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
        StoryResponse that = (StoryResponse) o;
        return estimate == that.estimate &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), estimate, status);
    }
}
