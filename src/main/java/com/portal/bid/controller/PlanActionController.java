package com.portal.bid.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portal.bid.entity.PlanAction;
import com.portal.bid.service.PlanActionService;
@RestController
@RequestMapping("/api/plans")
public class PlanActionController {

    @Autowired
    private PlanActionService planService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<PlanAction> createPlan(@RequestBody PlanAction plan) {
        plan.setCreatedAt(LocalDateTime.now());
        PlanAction createdPlan = planService.createPlan(plan);
        return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<List<PlanAction>> getAllPlans(@RequestParam(required = false) Long form_id) {
        List<PlanAction> plans;
        if (form_id != null) {
            plans = planService.getAllPlansByFormId(form_id);
        } else {
            plans = planService.getAllPlans();
        }
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public ResponseEntity<PlanAction> updatePlan(@PathVariable Long id, @RequestBody PlanAction plan) {
//        if (plan.getUpdatedAt() == null || plan.getUpdatedBy() == null || plan.getAction() == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Return 400 Bad Request if validation fails
//        }
        PlanAction updatedPlan = planService.updatePlan(id, plan);
        return updatedPlan != null ? new ResponseEntity<>(updatedPlan, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<PlanAction> getPlanById(@PathVariable Long id) {
        PlanAction plan = planService.getPlanById(id);
        return plan != null ? new ResponseEntity<>(plan, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//    @CrossOrigin(origins = "http://localhost:3000")
//    @GetMapping
//    public ResponseEntity<List<PlanAction>> getAllPlans() {
//        List<PlanAction> plans = planService.getAllPlans();
//        return new ResponseEntity<>(plans, HttpStatus.OK);
//    }
}
