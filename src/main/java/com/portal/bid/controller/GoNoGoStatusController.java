package com.portal.bid.controller;


import com.portal.bid.entity.GoNoGoStatus;
import com.portal.bid.service.GoNoGoStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/gonogostatus")
public class GoNoGoStatusController {

    @Autowired
    private GoNoGoStatusService goNoGoStatusService;

    @PostMapping
    ResponseEntity<GoNoGoStatus> createStatus(@RequestBody GoNoGoStatus entry){
        GoNoGoStatus createdEntry  = goNoGoStatusService.createEntry(entry);
        return ResponseEntity.ok(createdEntry);
    }


    @PutMapping("/{id}")
    ResponseEntity<GoNoGoStatus> updateStatus(@PathVariable  Long id ,@RequestBody GoNoGoStatus entry){
        entry.setCreatedAt(LocalDateTime.now());
//        if(entry.getCreatedBy()==null ){
//            return ResponseEntity.badRequest().build();
//        }
        GoNoGoStatus updatedEntry  = goNoGoStatusService.updateEntry(entry,id);
        return  updatedEntry!=null?ResponseEntity.ok(updatedEntry):ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    ResponseEntity<GoNoGoStatus>  getStatusById(@PathVariable Long id){
        GoNoGoStatus entryFound = goNoGoStatusService.findbyID(id);
        return entryFound!=null?ResponseEntity.ok(entryFound):ResponseEntity.notFound().build();
    }

    @GetMapping
    ResponseEntity<List<GoNoGoStatus>>  getAll(){
        List<GoNoGoStatus> entryFound = goNoGoStatusService.findAll();
        return ResponseEntity.ok(entryFound);
    }








}
