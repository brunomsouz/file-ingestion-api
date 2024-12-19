package io.souz.fileingestionapi.service;

import io.souz.fileingestionapi.domain.User;
import io.souz.fileingestionapi.dto.UserDto;
import io.souz.fileingestionapi.exception.NotFoundException;
import io.souz.fileingestionapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        this.userService = new UserService(this.userRepository);
    }

    @Test
    void findUserByIdWithValidId() {
        long userId = 1L;
        User user = new User(1L, "Bruno", new HashSet<>());
        when(this.userRepository.findUserById(userId)).thenReturn(Optional.of(user));

        UserDto result = this.userService.findUserById(userId);

        assertNotNull(result);
    }

    @Test
    void findUserByIdWithNonExistentId() {
        long userId = 1L;
        when(this.userRepository.findUserById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            this.userService.findUserById(userId);
        });

        assertEquals("User not found for given id.", exception.getMessage());
    }

}