package com.song.studycafe.user.dto;

import com.song.studycafe.UserRole;
import com.song.studycafe.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {

    private Long id;

    private String userId;

    private String password;

    private String email;

    private String phone;

    private String userName;


    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .userId(userId)
                .authority(UserRole.ADMIN)
                .email(email)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .build();
    }

    @Builder
    public UserRequestDto(Long id, String userId, String email, String password, String userName) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userId, password);
    }
}
