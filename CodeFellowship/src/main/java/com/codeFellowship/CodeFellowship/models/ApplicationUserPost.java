package com.codeFellowship.CodeFellowship.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ApplicationUserPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;
    String post;
    LocalDate time;

    @ManyToOne
    public ApplicationUser applicationUser;

    public ApplicationUserPost() {
    }

    public ApplicationUserPost(String post, ApplicationUser applicationUser, LocalDate time) {
        this.post = post;
        this.applicationUser = applicationUser;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    @Override
    public String toString() {
        return "ApplicationUserPost{" +
                "id=" + id +
                ", post='" + post + '\'' +
                ", time=" + time +
                '}';
    }
}

