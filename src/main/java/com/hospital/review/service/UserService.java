package com.hospital.review.service;

import com.hospital.review.domain.User;
import com.hospital.review.domain.dto.UserDto;
import com.hospital.review.domain.dto.UserJoinRequest;
import com.hospital.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto join(UserJoinRequest userJoinRequest) {
        log.info("user 정보 : " + userJoinRequest.getUserName() + ", " + userJoinRequest.getEmail() + ", " + userJoinRequest.getPassword());

        //비즈니스 로직 - 회원가입
        //아이디가 중복이면 회원가입 x --> 예외 발생
        userRepository.findByUserName(userJoinRequest.getUserName()).ifPresent(
                user -> {throw new RuntimeException("userName이 존재합니다");}
        );
        //중복이 아닐경우 회원가입
        User save = userRepository.save(userJoinRequest.toEntity());

        return UserDto.builder()
                .id(save.getId())
                .userName(save.getUserName())
                .email(save.getEmailAddress())
                .build();
    }
}
