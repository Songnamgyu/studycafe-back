package com.song.studycafe.user.repository;

import com.song.studycafe.user.domain.User;
import com.song.studycafe.user.dto.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);

    boolean existsByUserId(String userId);
}
