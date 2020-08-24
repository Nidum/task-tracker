package ag.pinguin.entity;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Issue {
    @Id
    @GeneratedValue
    protected UUID id;
    protected String title;
    protected String description;
    @PastOrPresent
    @NotNull
    protected LocalDate creationDate = LocalDate.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    protected Developer developer;

    public Issue() {
    }

    public Issue(String title, String description, @PastOrPresent LocalDate creationDate, Developer developer) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.developer = developer;
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

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(id, issue.id) &&
                Objects.equals(title, issue.title) &&
                Objects.equals(description, issue.description) &&
                Objects.equals(creationDate, issue.creationDate) &&
                Objects.equals(developer, issue.developer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, creationDate, developer);
    }
}
