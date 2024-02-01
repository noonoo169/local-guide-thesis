package com.example.localguidebe.service.impl;

import com.example.localguidebe.entity.Role;
import com.example.localguidebe.repository.RoleRepository;
import com.example.localguidebe.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @Override
    public List<Role> getRoleList() {
        return roleRepository.findAll();
    }
}
