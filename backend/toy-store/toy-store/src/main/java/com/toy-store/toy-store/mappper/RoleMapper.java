package com.petshop.petshop.mappper;

import com.petshop.petshop.mappper.dto.RoleDto;
import com.petshop.petshop.model.Role;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class RoleMapper {

    public String roleEntityToDto(Role role){
/*        return RoleDto.builder()
                .role(role)
                .build();*/
        return role.getRole();
    }

    public List<String> roleListEntityToDto(List<Role> roles){
        return roles.stream()
                .map(role -> roleEntityToDto(role))
                .toList();
    }

    public Role roleDtoToEntity(String roleDto){
        return Role.builder()
                .role(roleDto)
                .build();
    }

    public List<Role> roleListDtoToEntity(List<String> roleDtos){
        return roleDtos.stream()
                .map(roleDto -> roleDtoToEntity(roleDto))
                .toList();
    }
}
