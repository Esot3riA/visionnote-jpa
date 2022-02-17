package org.swm.visionnotejpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.swm.visionnotejpa.dto.UserRegisterDto;
import org.swm.visionnotejpa.entity.Avatar;
import org.swm.visionnotejpa.entity.UserRole;
import org.swm.visionnotejpa.entity.UserType;
import org.swm.visionnotejpa.repository.AvatarRepository;
import org.swm.visionnotejpa.repository.UserRoleRepository;
import org.swm.visionnotejpa.repository.UserTypeRepository;
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

        private final UserRoleRepository userRoleRepository;
        private final UserTypeRepository userTypeRepository;
        private final AvatarRepository avatarRepository;

        public void init() {
            initMemberRole();
            initMemberType();
            initDefaultAvatar();

            initAdminUser();
        }

        private void initMemberRole() {
            userRoleRepository.save(new UserRole("admin"));
            userRoleRepository.save(new UserRole("member"));
        }

        private void initMemberType() {
            userTypeRepository.save(new UserType("school"));
            userTypeRepository.save(new UserType("university"));
        }

        private void initDefaultAvatar() {
            avatarRepository.save(new Avatar("lalala.png"));
        }

        private void initAdminUser() {
            UserRegisterDto adminUserDto = new UserRegisterDto(
                    "esot3ria@gmail.com",
                    "1q2w3e4r",
                    "Esot3riA",
                    userTypeRepository.findUniversityType());

            userService.createAdmin(adminUserDto);
        }
    }
}
