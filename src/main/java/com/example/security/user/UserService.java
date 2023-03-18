package com.example.security.user;

import com.example.security.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {
        private final UserRepository userRepository;

        public List<GrantedAuthority> getAuthorities(User user) {
            return user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                    .collect(Collectors.toList());
        }

        public User findByUsername(String username) {
            return userRepository.findByUsername(username).get();
        }

    public UserDetails loadUserDetails(String username) {
        User user = findByUsername(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(getAuthorities(user))
                .build();
    }
}
