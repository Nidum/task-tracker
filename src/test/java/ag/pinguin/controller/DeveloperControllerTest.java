package ag.pinguin.controller;

import ag.pinguin.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static ag.pinguin.exception.Messages.INVALID_FIELD_VALUE;
import static ag.pinguin.exception.Messages.INVALID_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeveloperControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getValidation_invalidUUID() throws Exception {
        String invalidUUID = "test" + UUID.randomUUID().toString();
        String response = mockMvc.perform(get("/api/developer/{id}", invalidUUID))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse(String.format(INVALID_FIELD_VALUE, invalidUUID, "id"),
                HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void getValidation_blankUUID() throws Exception {
        String invalidUUID = " ";
        String response = mockMvc.perform(get("/api/developer/{id}", invalidUUID))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse("'get.id' must not be null", HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void create_nullFirstName() throws Exception {
        String response = mockMvc.perform(post("/api/developer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\": null}"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse("'firstName' must not be blank", HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void create_emptyFirstName() throws Exception {
        String response = mockMvc.perform(post("/api/developer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse("'firstName' must not be blank", HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void create_blankFirstName() throws Exception {
        String response = mockMvc.perform(post("/api/developer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\": \"    \"}"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse("'firstName' must not be blank", HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void updateValidation_invalidUUID() throws Exception {
        String invalidUUID = "test" + UUID.randomUUID().toString();
        String response = mockMvc.perform(put("/api/developer/{id}", invalidUUID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\": \"Mark\"}"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse(String.format(INVALID_FIELD_VALUE, invalidUUID, "id"),
                HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void updateValidation_blankUUID() throws Exception {
        String invalidUUID = " ";
        String response = mockMvc.perform(put("/api/developer/{id}", invalidUUID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\": \"Mark\"}"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse("'update.id' must not be null", HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void updateValidation_nullRequest() throws Exception {
        String id = UUID.randomUUID().toString();
        String response = mockMvc.perform(put("/api/developer/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse(INVALID_REQUEST, HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void updateValidation_emptyRequest() throws Exception {
        String id = UUID.randomUUID().toString();
        String response = mockMvc.perform(put("/api/developer/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse(
                List.of("'firstName' must not be blank"),
                HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void updateValidation_nullFirstName() throws Exception {
        String id = UUID.randomUUID().toString();
        String response = mockMvc.perform(put("/api/developer/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\": null}"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse("'firstName' must not be blank", HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void updateValidation_emptyFirstName() throws Exception {
        String id = UUID.randomUUID().toString();
        String response = mockMvc.perform(put("/api/developer/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse("'firstName' must not be blank", HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void updateValidation_blankFirstName() throws Exception {
        String id = UUID.randomUUID().toString();
        String response = mockMvc.perform(put("/api/developer/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\": \"    \"}"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse("'firstName' must not be blank", HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }


    @Test
    public void deleteValidation_invalidUUID() throws Exception {
        String invalidUUID = "test" + UUID.randomUUID().toString();
        String response = mockMvc.perform(delete("/api/developer/{id}", invalidUUID))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse(String.format(INVALID_FIELD_VALUE, invalidUUID, "id"),
                HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

    @Test
    public void deleteValidation_blankUUID() throws Exception {
        String invalidUUID = " ";
        String response = mockMvc.perform(delete("/api/developer/{id}", invalidUUID))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponse errorResponse = new ErrorResponse("'delete.id' must not be null", HttpStatus.BAD_REQUEST.value());
        assertEquals(mapper.writeValueAsString(errorResponse), response);
    }

}
