package com.song.studycafe.user.service;

import com.song.studycafe.user.dto.UserResponseDto;
import com.song.studycafe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto findUserInfoById(Long userId) {
        return userRepository.findById(userId)
                .map(UserResponseDto::getUserInfo)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));

    }

    public UserResponseDto findUserByUserId(Long userId) {
        return userRepository.findByUserId(userId)
                .map(UserResponseDto::getUserInfo)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }
}
