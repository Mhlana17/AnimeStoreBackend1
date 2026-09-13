package za.ac.cput.factory;
import org.junit.jupiter.api.TestMethodOrder;
import za.ac.cput.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.MethodName.class)

class UserFactoryTest {
    private static User user,user1;

    @BeforeEach
    void setup() {
        user = UserFactory.createUser("429536", "Phihlello", "phihlello.junaid@icloud.com");
        user1 = UserFactory.createUser("250118", "Mkhanyi", "mkhanyigmail.com");
    }

    @Test
    void a_testUser(){
        assertNotNull(user);
        System.out.print(user);
    }

    @Test
    void b_testUserThatFail() {
        assertNotNull(user1);
        System.out.println(user1);

    }
}