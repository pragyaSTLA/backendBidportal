package com.portal.bid.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "financial_year")
public class Fy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ob_fy",nullable = false)
    private int obFy;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name="created_at")
    private LocalDateTime updatedAt;

    @Column(name="created_by",nullable = false)
    private String createdBy;

    @Column(name="updated_by")
    private LocalDateTime updatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt =LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObFy() {
        return obFy;
    }

    public void setObFy(int obFy) {
        this.obFy = obFy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(LocalDateTime updatedBy) {
        this.updatedBy = updatedBy;
    }
}
