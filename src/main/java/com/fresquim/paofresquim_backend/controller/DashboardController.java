package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.dtos.DashboardResponseDTO;

import com.fresquim.paofresquim_backend.service.DashboardService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService
            dashboardService;

    public DashboardController(
            DashboardService dashboardService
    ) {

        this.dashboardService =
                dashboardService;
    }

    @GetMapping
    public ResponseEntity<
            DashboardResponseDTO
            > buscarDashboard() {

        return ResponseEntity.ok(
                dashboardService
                        .buscarDashboard()
        );
    }
}