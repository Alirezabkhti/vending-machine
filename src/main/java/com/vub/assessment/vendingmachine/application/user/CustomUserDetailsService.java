package com.vub.assessment.vendingmachine.application.user;


import java.util.Optional;

import com.vub.assessment.vendingmachine.domain.user.CustomUserDetails;
import com.vub.assessment.vendingmachine.domain.user.User;
import com.vub.assessment.vendingmachine.domain.user.UserJpaRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userJpaRepository.findByUsername(username);
        checkUserCredentialExistence(user);
        return new CustomUserDetails(user.get());
    }

    private void checkUserCredentialExistence(Optional<User> user) {
        if(!user.isPresent())
            throw new IllegalStateException("No user found!");
    }
}
