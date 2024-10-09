package com.petshop.petshop.service.impl;

import com.petshop.petshop.mappper.RoleMapper;
import com.petshop.petshop.mappper.dto.RoleDto;
import com.petshop.petshop.model.Role;
import com.petshop.petshop.repository.RoleRepository;
import com.petshop.petshop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Override
    public String getRoleDtoById(Long id){
        return roleMapper.roleEntityToDto(roleRepository.findById(id).orElse(null));
    }

    public String findByRoleDto(String role){
        return roleMapper.roleEntityToDto(roleRepository.findByRole(role));
    }

    public List<String> getAllRoleDtos(){
        return roleMapper.roleListEntityToDto(roleRepository.findAll());
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public String createRoleDto(Role role){
        return roleMapper.roleEntityToDto(roleRepository.save(role));
    }

    public String updateRoleDto(Role role){
        return roleMapper.roleEntityToDto(roleRepository.save(role));
    }

    public void deleteRole(Role role){
        roleRepository.delete(role);
    }

}