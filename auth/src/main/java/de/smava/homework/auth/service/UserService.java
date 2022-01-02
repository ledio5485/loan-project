package de.smava.homework.auth.service;

import de.smava.homework.auth.entity.UserEntity;
import de.smava.homework.auth.exception.UserAlreadyFoundException;
import de.smava.homework.auth.exception.UserNotFoundException;
import de.smava.homework.auth.model.UserDTO;
import de.smava.homework.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserDTO create(UserDTO userDTO) {
        log.info("create: Entering userDTO.getUsername(): {}", userDTO.getUsername());
        Optional<UserEntity> optionalPrevUser = userRepository.findByUsername(userDTO.getUsername());
        if (optionalPrevUser.isPresent()) {
            throw new UserAlreadyFoundException();
        }

        userDTO.setRoles("ROLE_USER");
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));

        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userDTO, user);
        UserEntity createdUser = userRepository.save(user);
        BeanUtils.copyProperties(createdUser, userDTO);
        userDTO.setPassword("******");
        log.info("Saved user: {}", userDTO);

        return userDTO;
    }

    public UserDTO get(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException();
        }
        UserDTO userDTO = new UserDTO();
        UserEntity user = optionalUser.get();
        log.info("Found user with id: {}", user.getId());
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
