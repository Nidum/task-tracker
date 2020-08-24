package ag.pinguin.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = "DEVELOPER")
public class Developer {
    @Id
    @GeneratedValue
    @Column(name = "developer_id")
    private UUID id;
    @Size(min = 1, max = 50)
    private String firstName;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Issue> assignedIssues;

    public Developer() {
    }

    public Developer(String firstName) {
        this.firstName = firstName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return Objects.equals(id, developer.id) &&
                Objects.equals(firstName, developer.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName);
    }
}
