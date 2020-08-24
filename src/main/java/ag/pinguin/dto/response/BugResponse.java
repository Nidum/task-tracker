package ag.pinguin.dto.response;

import ag.pinguin.domain.BugPriority;
import ag.pinguin.domain.BugStatus;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class BugResponse extends IssueResponse {
    private BugStatus status;
    private BugPriority priority;

    public BugResponse() {
    }

    public BugResponse(UUID id, String title, String description, LocalDate creationDate,
                       UUID developerId, BugStatus status, BugPriority priority) {
        super(id, title, description, creationDate, developerId);
        this.status = status;
        this.priority = priority;
    }

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    public BugPriority getPriority() {
        return priority;
    }

    public void setPriority(BugPriority priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BugResponse that = (BugResponse) o;
        return status == that.status &&
                priority == that.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status, priority);
    }
}
