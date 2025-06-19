package com.ecommerse.project.repositories;

import com.ecommerse.project.model.AppRole;
import com.ecommerse.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
