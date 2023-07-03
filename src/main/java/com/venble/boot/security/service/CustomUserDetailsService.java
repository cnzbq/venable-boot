package com.venble.boot.security.service;

import com.venble.boot.common.exception.ErrorCode;
import com.venble.boot.security.exception.UserNotActivatedException;
import com.venble.boot.security.exception.UserNotFoundException;
import com.venble.boot.system.domain.Role;
import com.venble.boot.system.domain.User;
import com.venble.boot.system.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage());
        }
        if (!user.isActivated()) {
            throw new UserNotActivatedException(ErrorCode.USER_NOT_ACTIVATED.getMessage());
        }
        List<GrantedAuthority> grantedAuthorities = user
                .getRoles()
                .stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}