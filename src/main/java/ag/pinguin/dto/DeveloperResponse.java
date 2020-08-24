package ag.pinguin.dto;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.UUID;

@ResponseBody
public class DeveloperResponse {
    private UUID id;
    private String firstName;

    public DeveloperResponse() {
    }

    public DeveloperResponse(UUID id, String firstName) {
        this.id = id;
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
        DeveloperResponse that = (DeveloperResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName);
    }
}
