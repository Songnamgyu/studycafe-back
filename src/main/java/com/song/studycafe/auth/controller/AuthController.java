package com.song.studycafe.auth.controller;

import com.song.studycafe.auth.service.AuthService;
import com.song.studycafe.jwt.TokenInfo;
import com.song.studycafe.user.dto.UserRequestDto;
import com.song.studycafe.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        System.out.println("arrive");
        return ResponseEntity.ok( "hello");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.signup(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.login(userRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenInfo> reissue(@RequestBody TokenInfo tokenInfo) {
        return ResponseEntity.ok(authService.reissue(tokenInfo));
    }


}
