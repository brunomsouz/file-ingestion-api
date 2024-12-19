package io.souz.fileingestionapi.service;

import io.souz.fileingestionapi.domain.User;
import io.souz.fileingestionapi.dto.UserDto;
import io.souz.fileingestionapi.exception.NotFoundException;
import io.souz.fileingestionapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto findUserById(Long id) {
        User user = this.userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException("user.not.found"));

        return UserDto.fromUser(user);
    }

}
