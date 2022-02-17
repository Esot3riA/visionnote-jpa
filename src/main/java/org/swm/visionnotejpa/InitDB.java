package org.swm.visionnotejpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.swm.visionnotejpa.dto.UserRegisterDto;
import org.swm.visionnotejpa.entity.Avatar;
import org.swm.visionnotejpa.entity.UserType;
import org.swm.visionnotejpa.repository.AvatarRepository;
import org.swm.visionnotejpa.service.UserService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final UserService userService;
        private final AvatarRepository avatarRepository;

        public void init() {
            initDefaultAvatar();
            initAdminUser();
        }

        private void initDefaultAvatar() {
            avatarRepository.save(new Avatar("lalala.png"));
        }

        private void initAdminUser() {
            UserRegisterDto adminUserDto = new UserRegisterDto(
                    "esot3ria@gmail.com", "1q2w3e4r", "Esot3riA", UserType.UNIVERSITY);

            userService.createAdmin(adminUserDto);
        }
    }
}
