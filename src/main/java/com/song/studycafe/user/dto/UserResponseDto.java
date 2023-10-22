package com.song.studycafe.user.dto;

import com.song.studycafe.UserRole;
import com.song.studycafe.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserResponseDto {

    private Long id;

    @Column(name="USER_ID")
    private String userId;

    @Column(name="USER_PASSWORD")
    private String password;

    @Column(name="EMAIL")
    private String email;

    @Column(name="PHONE")
    private String phone;

    @Column(name="USER_NAME")
    private String userName;

    @Enumerated(EnumType.STRING)
    private UserRole authority;

    public static UserResponseDto getUserInfo(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .authority(user.getAuthority())
                .build();

    }

}
