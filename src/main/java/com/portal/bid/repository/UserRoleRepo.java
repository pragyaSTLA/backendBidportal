package com.portal.bid.repository;

import com.portal.bid.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepo extends JpaRepository<UserRoles,Integer> {

}
