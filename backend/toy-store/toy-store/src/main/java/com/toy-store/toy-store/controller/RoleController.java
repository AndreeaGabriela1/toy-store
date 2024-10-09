package com.petshop.petshop.controller;

import com.petshop.petshop.controller.validation.GlobalExceptionHandlerController;
import com.petshop.petshop.mappper.dto.RoleDto;
import com.petshop.petshop.model.Role;
import com.petshop.petshop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class RoleController extends GlobalExceptionHandlerController {

    private final RoleService roleService;

    @GetMapping("/role")
    public String getRoles(Model model){
        List<String> roles = roleService.getAllRoleDtos();

        model.addAttribute("title", "Roles");
        model.addAttribute("roles", roles);

        return "roles";
    }

    @GetMapping("/role/{id}")
    public String getRoleById(@PathVariable Long id){
        return roleService.getRoleDtoById(id);
    }

    @PostMapping("/rolePost")
    public String postRole(@RequestBody Role role){
        return roleService.createRoleDto(role);
    }
}