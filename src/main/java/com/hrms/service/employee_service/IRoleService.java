package com.hrms.service.employee_service;

import java.util.List;

import com.hrms.entity.Role;

public interface IRoleService {

	List<Role> getAllRoles();
    Role getRoleById(Integer id);
    Role saveRole(Role role);
    void deleteRoleById(Integer id);
}
