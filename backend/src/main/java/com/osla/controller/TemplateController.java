package com.osla.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TemplateController {

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/settings")
    public String getSettingsPage() {
        return "b-side";
    }
    
}
