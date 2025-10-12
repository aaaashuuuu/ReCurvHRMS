package com.hrms.service.employee_service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.entity.Role;
import com.hrms.repository.employee_repository.IRoleRepository;

@Service
public class RoleServiceImpl implements IRoleService {

	private final IRoleRepository roleRepository;

    public RoleServiceImpl(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRoleById(Integer id) {
        roleRepository.deleteById(id);
    }
}
