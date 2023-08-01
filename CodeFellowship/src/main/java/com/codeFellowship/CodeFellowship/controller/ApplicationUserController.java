package com.codeFellowship.CodeFellowship.controller;

import com.codeFellowship.CodeFellowship.models.ApplicationUser;
import com.codeFellowship.CodeFellowship.repos.ApplicationUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;


@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView postSignUpData(String firstName, String lastName, LocalDate dateOfBirth, String bio, String username, String password) {
        ApplicationUser applicationUser = new ApplicationUser();

        applicationUser.setFirstName(firstName);
        applicationUser.setLastName(lastName);
        applicationUser.setDateOfBirth(dateOfBirth);
        applicationUser.setBio(bio);
        applicationUser.setUsername(username);
        applicationUser.setPassword(password);
        String encryptedPassword = passwordEncoder.encode(password);
        applicationUser.setPassword(encryptedPassword);

        applicationUserRepository.save(applicationUser);
        authWithHttpServletRequest(username, password);

        return new RedirectView("/");
    }
    public void authWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            System.out.println("Log in Error: ");
            e.printStackTrace();
        }
    }
    @GetMapping("/")
    public String getHomePage(Model m, Principal p) {

        System.out.println("Principal " + p);

        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

            m.addAttribute("username", username);
        }
        return "index.html";
    }
}
