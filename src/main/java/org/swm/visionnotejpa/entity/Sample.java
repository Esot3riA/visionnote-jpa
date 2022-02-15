package org.swm.visionnotejpa.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Sample {

    @Id @GeneratedValue
    private Long id;
}
