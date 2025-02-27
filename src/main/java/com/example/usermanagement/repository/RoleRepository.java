package com.example.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.usermanagement.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
//    Optional<RoleEntity> findByEmail(String email);
//    Optional<RoleEntity> findByRoleName(String roleName);
    @Query("SELECT r FROM RoleEntity r WHERE r.name = :roleName")
    Optional<RoleEntity> findByRoleName(@Param("roleName") String roleName);

}