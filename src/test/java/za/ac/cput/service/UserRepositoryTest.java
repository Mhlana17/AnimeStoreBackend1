//Author: Phihlello Junaid Maroga 219354359
package za.ac.cput.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.User;
import za.ac.cput.factory.UserFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private static User user;

    @BeforeEach
    public void setUp() {

        repository.deleteAll();

        user = UserFactory.createUser(
                "429536",
                "Phihlello",
                "phihlello.junaid@icloud.com"
        );
    }

    @Test
    void a_create() {
        User created = repository.save(user);
        assertNotNull(created);
        assertEquals(user.getUserId(), created.getUserId());
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        repository.save(user);
        Optional<User> read = repository.findById(user.getUserId());
        assertTrue(read.isPresent());
        System.out.println("Read: " + read.get());
    }

    @Test
    void c_update() {
        repository.save(user);

        User updatedUser = new User.Builder()
                .copy(user)
                .setUserName("PJ Maroga")
                .build();

        User updated = repository.save(updatedUser);
        assertNotNull(updated);
        assertEquals("PJ Maroga", updated.getUserName());
        System.out.println("Updated: " + updated);
    }

    @Test
    void d_delete() {
        repository.save(user);
        repository.deleteById(user.getUserId());
        Optional<User> deleted = repository.findById(user.getUserId());
        assertTrue(deleted.isEmpty());
        System.out.println("Deleted: true");
    }

    @Test
    void e_getAll() {
        repository.save(user);
        System.out.println(repository.findAll());
        assertFalse(repository.findAll().isEmpty());
    }
}

