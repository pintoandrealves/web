package com.aap.web.security;

import com.aap.web.models.UserEntity;
import com.aap.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findFirstByUsername(username);
        if(user != null) {
            return new User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                            .toList()
            );
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
