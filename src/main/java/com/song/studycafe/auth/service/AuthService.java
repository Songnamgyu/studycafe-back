package com.song.studycafe.auth.service;

import com.song.studycafe.jwt.RefreshTokenRepository;
import com.song.studycafe.jwt.TokenInfo;
import com.song.studycafe.jwt.TokenProvider;
import com.song.studycafe.jwt.domain.RefreshToken;
import com.song.studycafe.user.domain.User;
import com.song.studycafe.user.dto.UserRequestDto;
import com.song.studycafe.user.dto.UserResponseDto;
import com.song.studycafe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserResponseDto signup(UserRequestDto userRequestDto) {
        if(userRepository.existsByUserId(userRequestDto.getUserId())){
            throw new RuntimeException("이미 존재하는 유저 입니다.");
        }
        User user = userRequestDto.toUser(passwordEncoder);
        return UserResponseDto.getUserInfo(userRepository.save(user));
    }

    public TokenInfo login(UserRequestDto userRequestDto) {

        //1. Login 한 ID/PW 를 기반으로 AuthenicationToken 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication();

        //2. 실제로 검증(사용자 비밀번호 체크) 가 이루어지는곳
        // authenticate 메서드가 실행될때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //3. 인증 정보를 가지고 Token 생성
        TokenInfo token = tokenProvider.createToken(authenticate);

        //4. Refresh 토큰 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authenticate.getName())
                .key(token.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        //토큰 발행
        return token;
    }


    public TokenInfo reissue(TokenInfo tokenInfo) {
        //1. RefreshToken 검증
        if(tokenProvider.validateToken(tokenInfo.getRefreshToken())) {
            throw new RuntimeException(" 리프레쉬 토큰이 유효하지 않습니다 ");
        }

        //2. accessToken에서 userId 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenInfo.getAccessToken());

        //3. 저장소에서 userId를 가지고 Refesh토큰 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName()).orElseThrow(
                () -> new RuntimeException("로그 아웃된 사용자입니다."));

        //4. refresh 토큰 유효하지 검사
        if(!refreshToken.getValue().equals(tokenInfo.getRefreshToken())) {
            throw new RuntimeException("토근의 유저 정보가 일치하지 않습니다");
        }

        //5. 새로운 토큰 생성
        TokenInfo newToken = tokenProvider.createToken(authentication);
        
        //6 토큰 저장소 토큰 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(newToken.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        //토큰 발행
        return newToken;
    }
}
