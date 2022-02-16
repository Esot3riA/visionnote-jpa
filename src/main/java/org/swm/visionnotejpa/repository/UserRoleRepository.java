package org.swm.visionnotejpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.swm.visionnotejpa.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("select ur from UserRole ur where ur.name = 'admin'")
    UserRole findAdminRole();

    @Query("select ur from UserRole ur where ur.name = 'member'")
    UserRole findMemberRole();
}
