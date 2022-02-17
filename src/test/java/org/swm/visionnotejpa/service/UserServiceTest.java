package org.swm.visionnotejpa.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.swm.visionnotejpa.dto.UserRegisterDto;
import org.swm.visionnotejpa.entity.UserType;
import org.swm.visionnotejpa.exception.EmailDuplicatedException;
import org.swm.visionnotejpa.repository.UserRepository;
import org.swm.visionnotejpa.repository.UserTypeRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserTypeRepository userTypeRepository;

    @Test
    public void createUser() throws Exception {
        // given
        UserRegisterDto testUserDto = createTestUserDto("test@test.com");

        // when
        Long id = userService.createUser(testUserDto);

        // then
        assertThat(userRepository.findById(id).get().getEmail()).isEqualTo(testUserDto.getEmail());
    }

    @Test()
    public void createDuplicatedUser() throws Exception {
        // given
        UserRegisterDto testUserDto = createTestUserDto("test@test.com");

        // when
        userService.createUser(testUserDto);
        assertThatThrownBy(() -> userService.createUser(testUserDto))
                .isExactlyInstanceOf(EmailDuplicatedException.class);
    }

    @Test
    public void createNotValidUser() throws Exception {
        // given
        UserRegisterDto testUserDto = createTestUserDto("");

        // when
        assertThatThrownBy(() -> userService.createUser(testUserDto))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    private UserRegisterDto createTestUserDto(String email) {
        String password = "1q2w3e4r";
        String nickname = "test_user";
        UserType universityType = userTypeRepository.findUniversityType();
        return new UserRegisterDto(email, password, nickname, universityType);
    }

}