package com.portal.bid.controller;

import com.portal.bid.entity.Agp;
import com.portal.bid.service.AgpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agp")
public class AgpController {

    private final AgpService agpService;

    @Autowired
    public AgpController(AgpService agpService) {
        this.agpService = agpService;
    }

    @GetMapping
    public List<Agp> getAllAgps() {
        return agpService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agp> getAgpById(@PathVariable Long id) {
        Optional<Agp> agp = agpService.findById(id);
        return agp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Agp createAgp(@RequestBody Agp agp) {
        return agpService.save(agp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agp> updateAgp(@PathVariable Long id, @RequestBody Agp agpDetails) {
        Optional<Agp> agpOptional = agpService.findById(id);
//        System.out.println(agpOptional+"kdmfsdf");
        if (agpOptional.isPresent()) {
            Agp agp = agpOptional.get();
//            System.out.println("ispresent");
            LocalDate financialYearStart = LocalDate.of(LocalDate.now().getYear(), Month.APRIL, 1);
            LocalDate updateDeadline = financialYearStart.plusMonths(3);
            if (LocalDate.now().isAfter(updateDeadline)) {
//                System.out.println("date is agee");
                HttpHeaders headers = new HttpHeaders();
                headers.add("Error-Message", "AGP value updates are only allowed in the first three months of the financial year.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(null);
            }
            agp.setEmployeeID(agpDetails.getEmployeeID());
            agp.setAccountName(agpDetails.getAccountName());
            agp.setObFY(agpDetails.getObFY());
            agp.setObQT(agpDetails.getObQT());
            agp.setAgpValue(agpDetails.getAgpValue());
            agp.setCreatedBy(agpDetails.getCreatedBy());
            agp.setUpdatedBy(agpDetails.getUpdatedBy());
            agp.setStatus(agpDetails.getStatus());
            agp.setUpdatedAt(LocalDateTime.now());

            final Agp updatedAgp = agpService.save(agp);
            return ResponseEntity.ok(updatedAgp);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgp(@PathVariable Long id) {
        Optional<Agp> agpOptional = agpService.findById(id);
        if (agpOptional.isPresent()) {
            agpService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
