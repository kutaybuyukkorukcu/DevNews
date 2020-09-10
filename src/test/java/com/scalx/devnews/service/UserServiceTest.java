package com.scalx.devnews.service;

import com.scalx.devnews.entity.User;
import com.scalx.devnews.exception.UserExistsException;
import com.scalx.devnews.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    public void test_addUser_whenUserIsRegistered() {

        User user = new User("Josh","JoshAndHisPassword", "josh@email.com");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        doThrow(new UserExistsException())
                .when(userService).addUser(user);

        assertThatExceptionOfType(UserExistsException.class)
                .isThrownBy(() -> {
                    userService.addUser(user);
                });

        verify(userRepository).save(user);
        verifyNoMoreInteractions();
    }

    @Test
    public void test_addUser_whenUserIsNotRegistered() {
        User user = new User("Josh", "JoshAndHisPassword", "josh@email.com");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);

        userService.addUser(user);

        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_getUsers_whenFindAllIsNotPresent() {

        when(userRepository.findAll()).thenReturn(null);

        List<User> userList = userService.getUsers();
        List<User> expectedUserList = new ArrayList<>();

        assertThat(userList).isEqualTo(expectedUserList);

        verify(userRepository).findAll();
        verifyNoMoreInteractions(userService);
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
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_getUserByUsername_whenFindByUsernameIsNotPresent() {


    }

    @Test
    public void test_getUserByUsername_whenFindByUsernameIsPresent() {

    }

    @Test
    public void test_getUserById_whenFindByIdIsNotPresent() {

    }

    @Test
    public void test_getUserById_whenFindByIdIsPresent() {

    }

    @Test
    public void test_getUserByEmail_whenFindByEmailIsNotPresent() {

    }

    @Test
    public void test_getUserByEmail_whenFindByEmailIsPresent() {

    }
}
