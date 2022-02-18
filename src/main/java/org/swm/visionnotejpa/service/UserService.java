package org.swm.visionnotejpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.swm.visionnotejpa.dto.UserRegisterDto;
import org.swm.visionnotejpa.dto.UserUpdateDto;
import org.swm.visionnotejpa.entity.User;
import org.swm.visionnotejpa.entity.UserRole;
import org.swm.visionnotejpa.exception.EmailDuplicatedException;
import org.swm.visionnotejpa.repository.AvatarRepository;
import org.swm.visionnotejpa.repository.UserRepository;

import static org.springframework.util.StringUtils.*;

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

    @Transactional
    public void updateInfo(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유저 아이디입니다."));

        /**
         * 의문 : Null check 가 꼭 여기에 있어야 할까
         *          Entity 에서 이미 @NotBlank Validation 을 하는데 Dto 에서도 따로 하고 있고 Service update 에서도 따로 하고 있음
         *          update validation 정책은 모두 동일한데 하나로 합칠 수 있지 않을까..
         */
        if (hasText(userUpdateDto.getNickname())) {
            user.changeNickname(userUpdateDto.getNickname());
        }
        if (userUpdateDto.getType() != null) {
            user.changeType(userUpdateDto.getType());
        }
    }

    @Transactional
    public void updatePassword(Long id, String rawPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유저 아이디입니다."));

        if (hasText(rawPassword)) {
            user.changePassword(passwordEncoder.encode(rawPassword));
        }
    }
}
