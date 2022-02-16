package org.swm.visionnotejpa;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.swm.visionnotejpa.entity.Avatar;
import org.swm.visionnotejpa.entity.User;
import org.swm.visionnotejpa.entity.UserRole;
import org.swm.visionnotejpa.entity.UserType;
import org.swm.visionnotejpa.repository.AvatarRepository;
import org.swm.visionnotejpa.repository.UserRepository;
import org.swm.visionnotejpa.repository.UserRoleRepository;
import org.swm.visionnotejpa.repository.UserTypeRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

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

        private final EntityManager em;

        private final PasswordEncoder passwordEncoder;

        private final UserRoleRepository userRoleRepository;
        private final UserTypeRepository userTypeRepository;
        private final AvatarRepository avatarRepository;
        private final UserRepository userRepository;

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
            User adminUser = User.builder()
                    .email("esot3ria@gmail.com")
                    .nickname("Esot3riA")
                    .password(passwordEncoder.encode("1q2w3e4r"))
                    .role(userRoleRepository.findAdminRole())
                    .type(userTypeRepository.findUniversityType())
                    .avatar(avatarRepository.findDefaultAvatar())
                    .build();

            userRepository.save(adminUser);
        }
    }
}
