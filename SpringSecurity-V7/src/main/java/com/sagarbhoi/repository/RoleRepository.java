package com.sagarbhoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagarbhoi.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
}
