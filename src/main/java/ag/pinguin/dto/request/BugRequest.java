package ag.pinguin.dto.request;

import ag.pinguin.domain.BugPriority;
import ag.pinguin.domain.BugStatus;

import java.util.Objects;
import java.util.UUID;

public class BugRequest extends IssueRequest {
    private BugStatus status = BugStatus.NEW;
    private BugPriority priority;

    public BugRequest() {
    }

    public BugRequest(String title, String description,
                      UUID developerId, BugStatus status, BugPriority priority) {
        super(title, description, developerId);
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
        BugRequest that = (BugRequest) o;
        return status == that.status &&
                priority == that.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status, priority);
    }
}
