package org.swm.visionnotejpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.swm.visionnotejpa.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
