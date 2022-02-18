package org.swm.visionnotejpa.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.swm.visionnotejpa.dto.UserRegisterDto;
import org.swm.visionnotejpa.dto.UserUpdateDto;
import org.swm.visionnotejpa.entity.User;
import org.swm.visionnotejpa.entity.UserRole;
import org.swm.visionnotejpa.entity.UserType;
import org.swm.visionnotejpa.repository.UserRepository;
import org.swm.visionnotejpa.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/user/{id}")
    public UserDto findUser(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);

        return new UserDto(user
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유저 아이디입니다.")));
    }

    @PostMapping("/user")
    public CreateUserResponse saveUser(@RequestBody @Valid CreateUserRequest request) {
        UserRegisterDto userRegisterDto = new UserRegisterDto(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                request.getType());

        Long id = userService.createUser(userRegisterDto);
        return new CreateUserResponse(id);
    }

    @PutMapping("/user/{id}")
    public UpdateUserResponse updateUserInfo(
            @PathVariable @Valid Long id,
            @RequestBody @Valid UserUpdateDto userUpdateDto) {
        userService.updateInfo(id, userUpdateDto);

        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유저 아이디입니다."));
        return new UpdateUserResponse(updatedUser.getId());
    }

    @PutMapping("/user/pwchange/{id}")
    public UpdateUserResponse updateUserPassword(
            @PathVariable @Valid Long id,
            @RequestBody @Valid String password) {
        userService.updatePassword(id, password);

        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유저 아이디입니다."));
        return new UpdateUserResponse(updatedUser.getId());
    }

    @Data
    static class UserDto {
        private Long id;
        private String email;
        private String nickname;
        private UserType type;
        private UserRole role;
        private String avatar;

        public UserDto(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.type = user.getType();
            this.role = user.getRole();
            this.avatar = user.getAvatar().getSavedName();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class CreateUserRequest {
        private String email;
        private String password;
        private String nickname;
        private UserType type;
    }

    @Data
    @AllArgsConstructor
    static class CreateUserResponse {
        private Long id;
    }

    @Data
    @AllArgsConstructor
    static class UpdateUserResponse {
        private Long id;
    }
}
