package org.swm.visionnotejpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.swm.visionnotejpa.entity.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    @Query("select a from Avatar a where a.originalName = 'lalala.png'")
    Avatar findDefaultAvatar();
}
