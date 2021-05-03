package com.usermgmt.user.repository;

import com.usermgmt.user.model.UserPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPermissionsRepository extends JpaRepository<UserPermissions, UUID> {
    Optional<UserPermissions> findByPermissionId(UUID permissionId);
}
