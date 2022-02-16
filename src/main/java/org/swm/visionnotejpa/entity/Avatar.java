package org.swm.visionnotejpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Avatar extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "avatar_id")
    private Long id;

    private String originalName;
    private String savedName;

    public Avatar(String originalName) {
        changeAvatar(originalName);
    }

    private void changeAvatar(String avatarName) {
        originalName = avatarName;
        savedName = UUID.randomUUID() + avatarName;
    }
}
