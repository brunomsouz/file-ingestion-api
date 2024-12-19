package io.souz.fileingestionapi.controller;

import io.souz.fileingestionapi.domain.User;
import io.souz.fileingestionapi.dto.UserDto;
import io.souz.fileingestionapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id) {
        User user = this.userService.findUserById(id);

        return ResponseEntity.ok(UserDto.mapFromUser(user));
    }

}
