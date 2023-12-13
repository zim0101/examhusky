package com.app.examhusky.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/candidate/dashboard")
public class CandidateDashboardController {
    @GetMapping
    public String dashboard() {
        return "dashboard/candidate";
    }
}