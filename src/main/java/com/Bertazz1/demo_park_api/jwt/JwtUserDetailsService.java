package com.Bertazz1.demo_park_api.jwt;


import com.Bertazz1.demo_park_api.entity.User;
import com.Bertazz1.demo_park_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userService.findByUsername(username);
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String username) {
       User.Role role = userService.findRoleByUsername(username);
        return JwtUtis.createToken(username, role.name().substring("ROLE_".length()));
    }
}
