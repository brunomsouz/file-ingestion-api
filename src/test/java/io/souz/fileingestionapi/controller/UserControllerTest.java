package io.souz.fileingestionapi.controller;

import io.souz.fileingestionapi.dto.UserDto;
import io.souz.fileingestionapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    void setUp() {
        this.userController = new UserController(this.userService);
    }

    @Test
    void findUserByIdWithValidId() {
        Long userId = 1L;
        UserDto user = new UserDto(userId, "Bruno", new HashSet<>());
        when(this.userService.findUserById(userId)).thenReturn(user);

        ResponseEntity<UserDto> response = this.userController.findUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}