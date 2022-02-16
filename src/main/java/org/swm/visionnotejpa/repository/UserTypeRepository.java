package org.swm.visionnotejpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.swm.visionnotejpa.entity.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    @Query("select ut from UserType ut where ut.name = 'school'")
    UserType findSchoolType();

    @Query("select ut from UserType ut where ut.name = 'university'")
    UserType findUniversityType();
}
