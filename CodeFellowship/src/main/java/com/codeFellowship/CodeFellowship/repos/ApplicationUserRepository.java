package com.codeFellowship.CodeFellowship.repos;

import com.codeFellowship.CodeFellowship.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface ApplicationUserRepository extends JpaRepository <ApplicationUser, Long> {

    public ApplicationUser findByUsername(String username);

}
