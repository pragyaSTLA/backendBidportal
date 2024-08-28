package com.portal.bid.repository;

import com.portal.bid.entity.RolesPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface RolesPermissionRepo extends JpaRepository<RolesPermission,Integer> {

}
