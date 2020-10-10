package com.scalx.devnews.service;

import com.scalx.devnews.entity.User;
import com.scalx.devnews.exception.UserExistsException;
import com.scalx.devnews.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void test_addUser_whenUserIsRegistered() {

        User user = new User("Josh","JoshAndHisPassword", "josh@email.com");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        assertThatExceptionOfType(UserExistsException.class)
                .isThrownBy(() -> {
                    userService.addUser(user);
                });
    }

    @Test
    public void test_addUser_whenUserIsNotRegistered() {
        User user = new User("Josh", "JoshAndHisPassword", "josh@email.com");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);

        userService.addUser(user);

        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void test_getUsers_whenFindAllIsNotPresent() {

        when(userRepository.findAll()).thenReturn(null);

        List<User> userList = userService.getUsers();

        assertThat(userList).isEqualTo(new ArrayList<>());

        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_getUsers_whenFindAllIsPresent() {

        List<User> userList = new ArrayList<>();

        userList.add(new User("Josh", "JoshAndHisPassword", "josh@email.com"));
        userList.add(new User("Paul", "PaulAndHisPassword", "paul@email.com"));

        when(userRepository.findAll()).thenReturn(userList);

        List<User> expectedUserList = userService.getUsers();

        assertThat(userList).isEqualTo(expectedUserList);

        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_getUserByUsername_whenFindByUsernameIsNotPresent() {

        User user = new User("Josh", "JoshAndHisPassword", "josh@email.com");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);

        Optional<User> expectedUser = userService.getUserByUsername(user.getUsername());

        assertThat(expectedUser).isEqualTo(Optional.empty());

        verify(userRepository).findByUsername(user.getUsername());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_getUserByUsername_whenFindByUsernameIsPresent() {

        User user = new User("Josh", "JoshAndHisPassword", "josh@email.com");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        User expectedUser = userService.getUserByUsername(user.getUsername()).get();

        assertThat(user).isEqualTo(expectedUser);

        verify(userRepository).findByUsername(user.getUsername());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_getUserById_whenFindByIdIsNotPresent() {

        User user = new User("Josh", "JoshAndHisPassword", "josh@email.com");

        when(userRepository.findById(user.getId())).thenReturn(null);

        Optional<User> expectedUser = userService.getUserById(user.getId());

        assertThat(expectedUser).isEqualTo(Optional.empty());

        verify(userRepository).findById(user.getId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_getUserById_whenFindByIdIsPresent() {

        User user = new User("Josh", "JoshAndHisPassword", "josh@email.com");

        when(userRepository.findById(user.getId())).thenReturn(user);

        User expectedUser = userService.getUserById(user.getId()).get();

        assertThat(user).isEqualTo(expectedUser);

        verify(userRepository).findById(user.getId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_getUserByEmail_whenFindByEmailIsNotPresent() {

        User user = new User("Josh", "JoshAndHisPassword", "josh@email.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        Optional<User> expectedUser = userService.getUserByEmail(user.getEmail());

        assertThat(expectedUser).isEqualTo(Optional.empty());

        verify(userRepository).findByEmail(user.getEmail());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_getUserByEmail_whenFindByEmailIsPresent() {

        User user = new User("Josh", "JoshAndHisPassword", "josh@email.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        Optional<User> expectedUser = userService.getUserByEmail(user.getEmail());

        assertThat(user).isEqualTo(expectedUser.get());

        verify(userRepository).findByEmail(user.getEmail());
        verifyNoMoreInteractions(userRepository);
    }
}
