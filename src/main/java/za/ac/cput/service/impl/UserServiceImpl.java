//Author: Phihlello Junaid Maroga 219354359
package za.ac.cput.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.User;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.service.IUserService;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User create(User user) {
        if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
            return null;
        }
        User userToSave = user;
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            userToSave = new User.Builder()
                    .copy(user)
                    .setPassword(passwordEncoder.encode(user.getPassword()))
                    .build();
        }
        return userRepository.save(userToSave);
    }


    @Override
    public Optional<User> read(String id) {

        if (id == null || id.isEmpty()) {
            return Optional.empty();
        }

        return userRepository.findById(id);
    }


    @Override
    public User update(User user) {
        if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
            return null;
        }
        Optional<User> existingOpt = userRepository.findById(user.getUserId());
        if (existingOpt.isEmpty()) {
            return null;
        }
        User userToSave = user;
        String incomingPassword = user.getPassword();
        String storedPassword = existingOpt.get().getPassword();

        if (incomingPassword != null && !incomingPassword.isEmpty()
                && !incomingPassword.equals(storedPassword)) {
            userToSave = new User.Builder()
                    .copy(user)
                    .setPassword(passwordEncoder.encode(incomingPassword))
                    .build();
        } else if (incomingPassword == null || incomingPassword.isEmpty()) {
            userToSave = new User.Builder()
                    .copy(user)
                    .setPassword(storedPassword)
                    .build();
        }
        return userRepository.save(userToSave);
    }


    @Override
    public boolean delete(String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }


    @Override
    public List<User> searchByUserName(String userName) {
        if (userName == null || userName.isEmpty()) {
            return null;
        }
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> searchByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return Optional.empty();
        }
        return userRepository.findByEmail(email);
    }


    @Override
    public List<User> searchUsersByPattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return null;
        }
        return userRepository.findByUserNameContainingIgnoreCase(pattern);
    }

    @Override
    public Optional<User> login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return Optional.empty();
        }
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }
}