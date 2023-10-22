package com.song.studycafe.user.controller;

import com.song.studycafe.user.domain.User;
import com.song.studycafe.user.dto.UserResponseDto;
import com.song.studycafe.user.service.UserService;
import com.song.studycafe.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> findUserInfoById() {
        return ResponseEntity.ok(userService.findUserInfoById(SecurityUtil.getCurrentMemberId()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findUserByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findUserByUserId(userId));
    }
}
