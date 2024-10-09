package com.petshop.petshop.service;

import com.petshop.petshop.mappper.dto.RoleDto;
import com.petshop.petshop.model.Role;

import java.util.List;

public interface RoleService {

    public String getRoleDtoById(Long id);

    public String findByRoleDto(String role);

    public List<String> getAllRoleDtos();
    public List<Role> getAllRoles();

    public String createRoleDto(Role role);

    public String updateRoleDto(Role role);

    public void deleteRole(Role role);

}