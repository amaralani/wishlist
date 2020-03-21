/*
 * Acceptto Confidential
 *
 * This file is part of 'Atlas' module of the 'PAM' project.
 *
 * Copyright (c) 2017-2020 Acceptto Corporation All Rights Reserved.
 *
 * All information contained herein is, and remains the property
 * of Acceptto Corporation.
 * The intellectual and technical concepts contained herein are proprietary
 * to Acceptto Corporation and covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Acceptto Corporation.
 */

package ir.maralani.wishlist.security;

import ir.maralani.wishlist.domain.User;
import ir.maralani.wishlist.domain.UserDetailsWrapper;
import ir.maralani.wishlist.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple User details service which is backed by the main system datasource and
 * takes all required security polices into account before granting access.
 */
@Component
public class JwtUserDetailsService implements UserDetailsService {

    /**
     * Logger
     */
    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    /**
     * User service
     */
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        log.debug("Authenticating {}...", username);

        // Lookup the requested user by username, throw if not found
        User userDetails = userService.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found."));

        if (userDetails.isExternal())
            throw new UsernameNotFoundException("User does exist but default provider can't authenticate it.");

        // Check whether this user account is disabled by he administrators
        boolean isExpired = userDetails.isUserExpired();

        // Empty collection to load granted authorities into
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // Prepare the wrapped user details with the actual Id and return it
        return new UserDetailsWrapper(userDetails.getId(), username, userDetails.getPassword(),
                userDetails.getEnabled(), !isExpired, true, !userDetails.getLocked(), grantedAuthorities);
    }
}
