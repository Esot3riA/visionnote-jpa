package org.swm.visionnotejpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.swm.visionnotejpa.dto.UserRegisterDto;
import org.swm.visionnotejpa.entity.User;
import org.swm.visionnotejpa.entity.UserRole;
import org.swm.visionnotejpa.exception.EmailDuplicatedException;
import org.swm.visionnotejpa.repository.AvatarRepository;
import org.swm.visionnotejpa.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long createUser(UserRegisterDto userRegisterDto) {
        checkUserRegisterDto(userRegisterDto);

        User user = makeMember(userRegisterDto);
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public Long createAdmin(UserRegisterDto userRegisterDto) {
        checkUserRegisterDto(userRegisterDto);

        User user = makeAdmin(userRegisterDto);
        userRepository.save(user);
        return user.getId();
    }

    private void checkUserRegisterDto(UserRegisterDto userRegisterDto) throws RuntimeException {
        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new EmailDuplicatedException("이미 가입된 유저입니다.");
        }
        if (!userRegisterDto.isValid()) {
            throw new IllegalArgumentException("유저 정보가 잘못되었습니다.");
        }
    }

    private User makeMember(UserRegisterDto userRegisterDto) {
        User user = User.builder()
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .nickname(userRegisterDto.getNickname())
                .type(userRegisterDto.getType())
                .role(UserRole.MEMBER)
                .avatar(avatarRepository.findDefaultAvatar())
                .build();
        return user;
    }

    private User makeAdmin(UserRegisterDto userRegisterDto) {
        User user = User.builder()
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .nickname(userRegisterDto.getNickname())
                .type(userRegisterDto.getType())
                .role(UserRole.ADMIN)
                .avatar(avatarRepository.findDefaultAvatar())
                .build();
        return user;
    }

}
