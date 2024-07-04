package com.securityjwt.services;

import com.securityjwt.models.User;
import com.securityjwt.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private final User user = User.builder()
        .username("sampleUsername")
        .password("samplePassword")
        .build();

    @Test
    public void createUserTest() {

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.createUser(user);

        verify(userRepository, times(1)).save(user);

        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    public void findByUsernameExistTest() {

        when(userRepository.findByUsername("sampleUsername")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByUsername("sampleUsername");

        assertEquals(user.getUsername(), foundUser.get().getUsername());
        assertEquals(user.getPassword(), foundUser.get().getPassword());
    }

    @Test
    public void findByUsernameNonExistTest() {

        when(userRepository.findByUsername("nonExistUsername")).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findByUsername("nonExistUsername");

        assertEquals(Optional.empty(), foundUser);
    }

}