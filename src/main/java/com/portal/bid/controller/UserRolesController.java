package com.portal.bid.controller;

import com.portal.bid.entity.User;
import com.portal.bid.entity.UserRoles;
import com.portal.bid.service.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user-roles")
public class UserRolesController {

    @Autowired
    private UserRolesService userRolesService;

    @PostMapping
    public ResponseEntity<UserRoles> create(@RequestBody UserRoles userroles) {
        UserRoles u = userRolesService.save(userroles);
        return ResponseEntity.ok(u);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRoles> update(@PathVariable int id,@RequestBody UserRoles userroles){
        Optional<UserRoles> userRole = userRolesService.updateUser(id,userroles);
        return userRole.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserRoles> get(@PathVariable int id){
        Optional<UserRoles>  userRole = userRolesService.get(id);
        if(userRole.isPresent()){
            return  ResponseEntity.ok(userRole.get());
        }
        else{return ResponseEntity.notFound().build();}
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable int id){
        Optional<UserRoles>  userRole = userRolesService.get(id);
        if(userRole.isPresent()){
            userRolesService.delete(id);
            return  ResponseEntity.ok().build();
        }
        else{return ResponseEntity.notFound().build();}

    }

}
