package com.song.studycafe.user.service;

import com.song.studycafe.user.domain.User;
import com.song.studycafe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService  {

    private final UserRepository userRepository;
    @Transactional
    public UserDetails loadUserByUsername(Long userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new RuntimeException(userId + "찾을수 없습니다"));
    }

    private UserDetails createUserDetails (User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getId()),
                user.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
