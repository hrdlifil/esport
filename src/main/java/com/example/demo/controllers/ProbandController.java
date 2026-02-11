package com.example.demo.controllers;

import com.example.demo.domain.Proband;
import com.example.demo.services.EmailService;
import com.example.demo.services.ProbandService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProbandController {

    private final ProbandService probandService;
    private final EmailService emailService;

    public ProbandController(ProbandService probandService, EmailService emailService) {
        this.probandService = probandService;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("proband", new Proband());
        return "index";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("proband") Proband proband,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "index";
        }

        try {
            probandService.registerProband(proband);
        } catch (IllegalStateException e) {
            model.addAttribute("emailError", e.getMessage());
            return "index";
        }

        // Send confirmation email
        emailService.sendConfirmationEmail(proband);

        redirectAttributes.addFlashAttribute("successMessage",
                "Děkujeme za registraci! Brzy vás budeme kontaktovat s dalšími podrobnostmi.");
        return "redirect:/success";
    }

    @GetMapping("/register")
    public String registerRedirect() {
        return "redirect:/";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }
}
