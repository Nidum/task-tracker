package ag.pinguin.dto.response;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public abstract class IssueResponse {
    protected UUID id;
    protected String title;
    protected String description;
    protected LocalDate creationDate;
    protected UUID developerId;

    public IssueResponse() {
    }

    public IssueResponse(UUID id, String title, String description, LocalDate creationDate, UUID developerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.developerId = developerId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public UUID getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(UUID developerId) {
        this.developerId = developerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueResponse that = (IssueResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(developerId, that.developerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, creationDate, developerId);
    }
}
