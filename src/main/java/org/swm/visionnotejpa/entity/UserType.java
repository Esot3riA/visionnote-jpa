package org.swm.visionnotejpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserType extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "type_id")
    private Long id;

    @Column(name = "type_name")
    private String name;

    public UserType(String name) {
        this.name = name;
    }
}
