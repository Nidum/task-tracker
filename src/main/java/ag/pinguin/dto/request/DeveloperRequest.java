package ag.pinguin.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class DeveloperRequest {
    @NotBlank
    private String firstName;

    public DeveloperRequest() {
    }

    public DeveloperRequest(String firstName) {
        this.firstName = firstName;
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
        DeveloperRequest that = (DeveloperRequest) o;
        return Objects.equals(firstName, that.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName);
    }
}
