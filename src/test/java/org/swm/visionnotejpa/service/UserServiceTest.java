package org.swm.visionnotejpa.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.swm.visionnotejpa.dto.UserRegisterDto;
import org.swm.visionnotejpa.dto.UserUpdateDto;
import org.swm.visionnotejpa.entity.User;
import org.swm.visionnotejpa.entity.UserType;
import org.swm.visionnotejpa.exception.EmailDuplicatedException;
import org.swm.visionnotejpa.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void createUser() throws Exception {
        // given
        UserRegisterDto testUserDto = createTestUserDto("test@test.com");

        // when
        Long id = userService.createUser(testUserDto);

        // then
        assertThat(userRepository.findById(id).get().getEmail()).isEqualTo(testUserDto.getEmail());
    }

    @Test
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

    @Test
    public void changeType() throws Exception {
        // given
        UserRegisterDto testUserDto = createTestUserDto("test@test.com");
        Long id = userService.createUser(testUserDto);

        em.flush();
        em.clear();

        // when
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                        .type(UserType.SCHOOL)
                        .build();
        userService.updateInfo(id, userUpdateDto);

        em.flush();
        em.clear();

        // then
        User changedUser = userRepository.getById(id);
        assertThat(changedUser.getType()).isEqualTo(UserType.SCHOOL);
        assertThat(changedUser.getNickname()).isEqualTo("test_user");
    }

    @Test
    public void changeNickname() throws Exception {
        // given
        UserRegisterDto testUserDto = createTestUserDto("test@test.com");
        Long id = userService.createUser(testUserDto);

        // when
        String changedNickname = "Esot3riA";
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .nickname(changedNickname)
                .build();
        userService.updateInfo(id, userUpdateDto);

        // then
        User changedUser = userRepository.getById(id);
        assertThat(changedUser.getType()).isEqualTo(UserType.UNIVERSITY);
        assertThat(changedUser.getNickname()).isEqualTo(changedNickname);
    }

    @Test
    public void changeShortNickname() throws Exception {
        // given
        UserRegisterDto testUserDto = createTestUserDto("test@test.com");
        Long id = userService.createUser(testUserDto);

        // when
        String changedNickname = "A";
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .nickname(changedNickname)
                .build();

        userService.updateInfo(id, userUpdateDto);

        assertThatThrownBy(() -> em.flush())
                .isExactlyInstanceOf(ConstraintViolationException.class);
    }

    private UserRegisterDto createTestUserDto(String email) {
        String password = "1q2w3e4r";
        String nickname = "test_user";
        UserType universityType = UserType.UNIVERSITY;
        return new UserRegisterDto(email, password, nickname, universityType);
    }

}