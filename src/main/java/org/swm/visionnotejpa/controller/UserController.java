package org.swm.visionnotejpa.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.swm.visionnotejpa.entity.User;
import org.swm.visionnotejpa.repository.UserRepository;
import org.swm.visionnotejpa.service.UserService;

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

    @Data
    static class UserDto {
        private Long id;
        private String email;
        private String nickname;
        private String type;
        private String role;
        private String avatar;

        public UserDto(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.type = user.getType().getName();
            this.role = user.getRole().getName();
            this.avatar = user.getAvatar().getSavedName();
        }
    }
}
