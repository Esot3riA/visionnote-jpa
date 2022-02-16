package org.swm.visionnotejpa.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "type_id")
    private UserType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "role_id")
    private UserRole role;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    private String email;
    private String password;
    private String nickname;
}
