//Author: Phihlello Junaid Maroga 219354359
package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


    List<User> findByUserName(String userName);


    Optional<User> findByEmail(String email);


    List<User> findByUserNameContainingIgnoreCase(String pattern);
}