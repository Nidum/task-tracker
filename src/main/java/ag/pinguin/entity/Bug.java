package ag.pinguin.entity;

import ag.pinguin.domain.BugPriority;
import ag.pinguin.domain.BugStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "BUG")
public class Bug extends Issue{
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private BugStatus status;
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private BugPriority priority;

    public Bug() {
    }

    public Bug(String title, String description, LocalDate creationDate, Developer developer,
               BugStatus status, BugPriority priority) {
        super(title, description, creationDate, developer);
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
        Bug bug = (Bug) o;
        return status == bug.status &&
                priority == bug.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status, priority);
    }
}
