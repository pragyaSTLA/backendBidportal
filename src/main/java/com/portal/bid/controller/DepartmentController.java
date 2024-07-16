package com.portal.bid.controller;


import com.portal.bid.entity.DealStatus;
import com.portal.bid.entity.Department;
import com.portal.bid.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private DepartmentService departmentService;

    @PostMapping
    public Department createDepartment(@RequestBody Department dep){
        Department dep_created = departmentService.saveDepartment(dep);
        return dep_created;
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartment(){
        List<Department> list = departmentService.getAllDepartment();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id,@RequestBody Department dep){
        Department updatedDep = departmentService.updateDepartment(dep);
        return updatedDep!=null?new ResponseEntity<>(updatedDep,HttpStatus.OK):new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id){
        boolean isDeleted = departmentService.deleteDepartment(id);
        return isDeleted? new ResponseEntity<>(HttpStatus.NO_CONTENT):new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
