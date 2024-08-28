package com.portal.bid.repository;

import com.portal.bid.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Integer> {
    // Custom queries can be added here if needed

}
