package com.scalx.devnews.service;

import com.scalx.devnews.entity.PasswordResetToken;
import com.scalx.devnews.entity.User;
import com.scalx.devnews.entity.VerificationToken;
import com.scalx.devnews.exception.UserAlreadyExistException;
import com.scalx.devnews.repository.PasswordResetTokenRepository;
import com.scalx.devnews.repository.RoleRepository;
import com.scalx.devnews.repository.UserRepository;
import com.scalx.devnews.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private Environment environment;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    // TODO : Implement DTO's
    public User registerUser(final User userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) == null) {
            throw new UserAlreadyExistException("There is an account with that email address : " + userDto.getEmail());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        // TODO : Client sends encoded password to API. Using PasswordEncoder till Integration tests.
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.saveAndFlush(user);
    }

    public User getUser(String verificationToken) {
        VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }

        return null;
    }

    public VerificationToken getVerificationToken(final String VerificationToken) {
        return verificationTokenRepository.findByToken(VerificationToken);
    }

    public void createVerificationTokenForUser(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);
    }

    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(existingVerificationToken);

        verificationToken.updateToken(UUID.randomUUID()
                .toString());
        verificationToken = verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    public List<User> getUsers() {
        List<User> userList = userRepository.findAll();

        if (userList == null) {
            return Collections.emptyList();
        }

        return userList;
    }

    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    public Optional<User> findByUsername(String username) {
        User user = userRepository.findByUsername(username);

        return Optional.ofNullable(user);
    }

    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

    public Optional<User> findById(int id) {
        User user = userRepository.findById(id);

        return Optional.ofNullable(user);
    }

    public Optional<User> findByEmail(String email) {
        User user = userRepository.findByEmail(email);

        return Optional.ofNullable(user);
    }


}
