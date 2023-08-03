package com.codeFellowship.CodeFellowship.controller;

import com.codeFellowship.CodeFellowship.models.ApplicationUser;
import com.codeFellowship.CodeFellowship.models.ApplicationUserPost;
import com.codeFellowship.CodeFellowship.repos.ApplicationUserPostRepository;
import com.codeFellowship.CodeFellowship.repos.ApplicationUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    ApplicationUserPostRepository applicationUserPostRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/userProfile/{id}")
    public String getUserProfilePage(@PathVariable Long id, Model model, Principal p) {
        ApplicationUser user = applicationUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        String imageURL = "https://images.unsplash.com/photo-1599508704512-2f19efd1e35f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8cXVlc3Rpb24lMjBtYXJrfGVufDB8fDB8fHww&auto=format&fit=crop&w=500&q=60";
        user.setImageURL(imageURL);
        model.addAttribute("user", user);

        if (p != null) {
            String username = p.getName();
            ApplicationUser currentUser = applicationUserRepository.findByUsername(username);
            model.addAttribute("username", username);
            model.addAttribute("currentUser", currentUser);
        }

        return "userProfile";
    }


    @GetMapping("/myprofile")
    public String getmyProfilePage(Model m, Principal p) {

        System.out.println("Principal " + p);

        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

            String imageURL = "https://images.unsplash.com/photo-1599508704512-2f19efd1e35f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8cXVlc3Rpb24lMjBtYXJrfGVufDB8fDB8fHww&auto=format&fit=crop&w=500&q=60";
            applicationUser.setImageURL(imageURL);

            m.addAttribute("applicationUser", applicationUser);
            m.addAttribute("username", username);

        }
            return "/myprofile";
    }

    @PostMapping("/myprofile")
    public RedirectView postMyUserPosts(Principal p, String userPost) {
        String username = p.getName();
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        LocalDate time = LocalDate.now();
        ApplicationUserPost newPost = new ApplicationUserPost(userPost, applicationUser, time);
        applicationUserPostRepository.save(newPost);

        return new RedirectView("/myprofile");
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

    @GetMapping("/users")
    public String getAllUsersPage(Model m, Principal p) {
        List<ApplicationUser> users = applicationUserRepository.findAll();
        m.addAttribute("usersArray", users);

        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            String imageURL = "https://images.unsplash.com/photo-1599508704512-2f19efd1e35f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8cXVlc3Rpb24lMjBtYXJrfGVufDB8fDB8fHww&auto=format&fit=crop&w=500&q=60";
            applicationUser.setImageURL(imageURL);

            m.addAttribute("username", username);
            m.addAttribute("currentUser", applicationUser);
        }
        return "users";
    }

    @PutMapping("/follow-user/{id}")
    public RedirectView followUser(Principal p, @PathVariable Long id) {
        ApplicationUser userToFollow = applicationUserRepository.findById(id).orElseThrow(() -> new RuntimeException("Error reading this user from Database: " + id));

        ApplicationUser currentUser = applicationUserRepository.findByUsername((p.getName()));

        if(currentUser.getUsername().equals((userToFollow.getUsername()))) {
            throw new IllegalArgumentException("Following yourself is not allowed");
        }
        currentUser.getUsersIFollow().add(userToFollow);
        applicationUserRepository.save(currentUser);

        return new RedirectView("/users");
    }

    @GetMapping("/feed")
    public String getFeedPage(Model m, Principal p) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser currentUser = applicationUserRepository.findByUsername(username);
            Set<ApplicationUser> following = currentUser.getUsersIFollow();

            // get all posts from users the current user is following
            List<ApplicationUserPost> feedPosts = new ArrayList<>();
            for (ApplicationUser user : following) {
                feedPosts.addAll(user.getPostArray());
            }

            m.addAttribute("username", username);
            m.addAttribute("feedPosts", feedPosts);
        }
        return "feed";
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
