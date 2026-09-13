//Author: Phihlello Junaid Maroga 219354359
package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.ac.cput.domain.User;
import za.ac.cput.service.IUserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    public void setUp() {
        userService.getAll().forEach(user -> userService.delete(user.getUserId()));

        testUser = new User.Builder()
                .setUserId("user123")
                .setUserName("John Doe")
                .setEmail("john@example.com")
                .build();
    }

    @Test
    public void testCreateUser_Success() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.userName").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testReadUser_Success() throws Exception {
        userService.create(testUser);

        mockMvc.perform(get("/api/users/{userId}", "user123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.userName").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testReadUser_NotFound() throws Exception {
        mockMvc.perform(get("/api/users/{userId}", "nonexistent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUsers_Success() throws Exception {
        userService.create(testUser);

        User secondUser = new User.Builder()
                .setUserId("user456")
                .setUserName("Jane Smith")
                .setEmail("jane@example.com")
                .build();
        userService.create(secondUser);

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userId").value("user123"))
                .andExpect(jsonPath("$[1].userId").value("user456"));
    }

    @Test
    public void testUpdateUser_Success() throws Exception {
        userService.create(testUser);

        User updatedUser = new User.Builder()
                .setUserId("user123")
                .setUserName("John Updated")
                .setEmail("john.updated@example.com")
                .build();

        mockMvc.perform(put("/api/users/{userId}", "user123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.userName").value("John Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"));
    }

    @Test
    public void testUpdateUser_NotFound() throws Exception {
        mockMvc.perform(put("/api/users/{userId}", "nonexistent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        userService.create(testUser);

        mockMvc.perform(delete("/api/users/{userId}", "user123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/{userId}", "user123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        mockMvc.perform(delete("/api/users/{userId}", "nonexistent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchByUserName_Success() throws Exception {
        userService.create(testUser);

        mockMvc.perform(get("/api/users/search/username/{userName}", "John Doe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName").value("John Doe"));
    }

    @Test
    public void testSearchByUserName_NotFound() throws Exception {
        mockMvc.perform(get("/api/users/search/username/{userName}", "NonExistent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testSearchByEmail_Success() throws Exception {
        userService.create(testUser);

        mockMvc.perform(get("/api/users/search/email/{email}", "john@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testSearchByEmail_NotFound() throws Exception {
        mockMvc.perform(get("/api/users/search/email/{email}", "nonexistent@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchByPattern_Success() throws Exception {
        userService.create(testUser);

        User secondUser = new User.Builder()
                .setUserId("user789")
                .setUserName("Johnny Appleseed")
                .setEmail("johnny@example.com")
                .build();
        userService.create(secondUser);

        mockMvc.perform(get("/api/users/search/pattern")
                        .param("pattern", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testSearchByPattern_NoMatches() throws Exception {
        userService.create(testUser);

        mockMvc.perform(get("/api/users/search/pattern")
                        .param("pattern", "NonExistent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
