package com.song.studycafe.user.domain;

import com.song.studycafe.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
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




    @Builder
    public User(Long id, String userId, String email, String password, String userName,UserRole authority) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.authority = authority;
    }
}
