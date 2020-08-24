package ag.pinguin.integration;

import ag.pinguin.dto.response.DeveloperResponse;
import ag.pinguin.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static ag.pinguin.exception.Messages.ENTITY_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeveloperControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql({"drop_data.sql", "insert_developers.sql"})
    public void getSingle_existingId() throws Exception {
        var id = UUID.fromString("3b43c8c6-e5f7-11ea-adc1-0242ac120002");
        String response = mockMvc.perform(get("/api/developer/{id}", id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        DeveloperResponse actualResponse = new DeveloperResponse(id, "Ann");
        assertEquals(mapper.writeValueAsString(actualResponse), response);
    }

    @Test
    @Sql({"drop_data.sql", "insert_developers.sql"})
    public void getSingle_NonExistingId() throws Exception {
        String idString = "3b43c8c6-e5f7-11ea-adc1-0242ac120020";
        var id = UUID.fromString(idString);
        String response = mockMvc.perform(get("/api/developer/{id}", id))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse(String.format(ENTITY_NOT_FOUND, idString), HttpStatus.NOT_FOUND.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    @Sql({"drop_data.sql", "insert_developers.sql"})
    public void getAll() throws Exception {
        String response = mockMvc.perform(get("/api/developer"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<DeveloperResponse> expectedResponse = List.of(
                new DeveloperResponse(UUID.fromString("3b43c8c6-e5f7-11ea-adc1-0242ac120001"), "Mary"),
                new DeveloperResponse(UUID.fromString("3b43c8c6-e5f7-11ea-adc1-0242ac120002"), "Ann"),
                new DeveloperResponse(UUID.fromString("3b43c8c6-e5f7-11ea-adc1-0242ac120003"), "David"),
                new DeveloperResponse(UUID.fromString("3b43c8c6-e5f7-11ea-adc1-0242ac120004"), "Ben"),
                new DeveloperResponse(UUID.fromString("3b43c8c6-e5f7-11ea-adc1-0242ac120005"), "Harold")
        );

        assertEquals(mapper.writeValueAsString(expectedResponse), response);
    }

    @Test
    @Sql("drop_data.sql")
    public void getAll_EmptyDb() throws Exception {
        String response = mockMvc.perform(get("/api/developer"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<DeveloperResponse> expectedResponse = List.of();

        assertEquals(mapper.writeValueAsString(expectedResponse), response);
    }

    @Test
    @Sql("drop_data.sql")
    public void create_EmptyDb() throws Exception {
        String response = mockMvc.perform(post("/api/developer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\": \"Mark\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Mark"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        var responseDto = mapper.readValue(response, DeveloperResponse.class);
        String maybeMark = mockMvc.perform(get("/api/developer/{id}", responseDto.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(responseDto, mapper.readValue(maybeMark, DeveloperResponse.class));
    }

    @Test
    @Sql({"drop_data.sql", "insert_developers.sql"})
    public void create_NonEmptyDbSameName_NewIdAssigned() throws Exception {
        String response = mockMvc.perform(post("/api/developer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\": \"Ann\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Ann"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        var responseDto = mapper.readValue(response, DeveloperResponse.class);
        assertEquals("Ann", responseDto.getFirstName());
        assertNotEquals("3b43c8c6e5f711eaadc10242ac120002", responseDto.getId().toString());

        String maybeAnn = mockMvc.perform(get("/api/developer/{id}", responseDto.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(responseDto, mapper.readValue(maybeAnn, DeveloperResponse.class));
    }

    @Test
    @Sql({"drop_data.sql", "insert_developers.sql"})
    public void delete_existingId() throws Exception {
        var id = UUID.fromString("3b43c8c6-e5f7-11ea-adc1-0242ac120002");
        mockMvc.perform(delete("/api/developer/{id}", id))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/developer/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql({"drop_data.sql", "insert_developers.sql"})
    public void delete_nonExistingId() throws Exception {
        var id = UUID.fromString("3b43c8c6-e5f7-11ea-adc1-0242ac120020");
        mockMvc.perform(get("/api/developer/{id}", id))
                .andExpect(status().isNotFound());
    }

}
