package com.application.winelibrary.repository.role;

import com.application.winelibrary.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(Role.RoleName name);
}
