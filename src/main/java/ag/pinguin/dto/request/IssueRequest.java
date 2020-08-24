package ag.pinguin.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.UUID;

public abstract class IssueRequest {
    @NotBlank
    protected String title;
    @NotBlank
    protected String description;
    protected UUID developerId;

    public IssueRequest() {
    }

    public IssueRequest(String title, String description, UUID developerId) {
        this.title = title;
        this.description = description;
        this.developerId = developerId;
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
        IssueRequest that = (IssueRequest) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(developerId, that.developerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, developerId);
    }
}
