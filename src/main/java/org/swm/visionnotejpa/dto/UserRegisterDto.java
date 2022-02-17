package org.swm.visionnotejpa.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.swm.visionnotejpa.entity.UserType;

import static org.springframework.util.StringUtils.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegisterDto {
    String email;
    String password;
    String nickname;
    UserType type;

    public UserRegisterDto(String email, String password, String nickname, UserType type) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.type = type;
    }

    public boolean isValid() {
        return hasText(email) && hasText(password) && hasText(nickname) && type != null;
    }
}
