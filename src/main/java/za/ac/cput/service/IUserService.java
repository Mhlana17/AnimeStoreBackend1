//Author: Phihlello Junaid Maroga 219354359
package za.ac.cput.service;

import za.ac.cput.domain.User;

import java.util.List;
import java.util.Optional;


public interface IUserService extends IService<User, String> {


    List<User> searchByUserName(String userName);


    Optional<User> searchByEmail(String email);


    List<User> searchUsersByPattern(String pattern);

    Optional<User> login(String email, String password);
}