package org.swm.visionnotejpa.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.swm.visionnotejpa.entity.UserType;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserUpdateDto {

    @Length(min = 2, max = 16)
    private String nickname;

    private UserType type;
}
