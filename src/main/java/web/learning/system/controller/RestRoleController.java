package web.learning.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.learning.system.domain.Role;
import web.learning.system.domain.User;
import web.learning.system.service.RoleService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/role")
public class RestRoleController {

    private final RoleService roleService;

    @Autowired
    public RestRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRole() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }
}
