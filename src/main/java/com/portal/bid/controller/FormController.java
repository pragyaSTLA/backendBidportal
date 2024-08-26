package com.portal.bid.controller;

import com.portal.bid.dto.OpportunitiesResponse;
import com.portal.bid.entity.Form;
import com.portal.bid.service.OpportunityService;
import com.portal.bid.service.implementation.OpportunityServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
public class FormController{



    @Autowired
    private OpportunityServiceImp opportunityService;

//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<Form> createOpportunity(@RequestBody Form opportunity) {

        Form savedOpportunity = opportunityService.saveOpportunity(opportunity);
        return ResponseEntity.ok(savedOpportunity);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<List<Form>> getAllOpportunities() {
        List<Form> opportunities = opportunityService.getAllOpportunities();
        return ResponseEntity.ok(opportunities);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<Form> getOpportunityById(@PathVariable Long id) {
        Form opportunity = opportunityService.getOpportunityById(id);
        if (opportunity != null) {
            return ResponseEntity.ok(opportunity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public ResponseEntity<Form> updateOpportunity(@PathVariable Long id, @RequestBody Form updatedOpportunity) {
        Form updated = opportunityService.updateOpportunity(id, updatedOpportunity);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/filtered")
    public ResponseEntity<?> getOpportunities(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String ob_fy,
            @RequestParam(required = false) String business_unit,
            @RequestParam(required = false) String industry_segment,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date,
            @RequestParam(required = false) String responsible_person,
            @RequestParam(required = false) String customer,
            @RequestParam(required = false) BigDecimal deal_value_min,
            @RequestParam(required = false) BigDecimal deal_value_max) {
//        if(end_date!=null){
////                 System.out.println(end_date);
//        }


        List<Form> filteredOpportunities = opportunityService.getFilteredOpportunities(
                status, priority,ob_fy, business_unit, industry_segment, start_date, end_date,
                responsible_person, customer, deal_value_min, deal_value_max);

        int totalOpportunities = filteredOpportunities.size();

        return ResponseEntity.ok(new OpportunitiesResponse(totalOpportunities, filteredOpportunities));
    }
}
