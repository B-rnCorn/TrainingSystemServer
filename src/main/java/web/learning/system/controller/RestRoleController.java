package web.learning.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.learning.system.domain.Role;
import web.learning.system.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("api/role")
@Api(value = "", tags = {"Работа с ролями пользователей"})
public class RestRoleController {

    private final RoleService roleService;

    @Autowired
    public RestRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @ApiOperation(value = "Получить все роли")
    public ResponseEntity<List<Role>> getAllRole() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }
}
