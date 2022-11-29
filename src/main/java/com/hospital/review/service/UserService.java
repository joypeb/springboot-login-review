package com.hospital.review.service;

import com.hospital.review.domain.User;
import com.hospital.review.domain.dto.UserDto;
import com.hospital.review.domain.dto.UserJoinRequest;
import com.hospital.review.exception.ErrorCode;
import com.hospital.review.exception.HospitalReviewAppException;
import com.hospital.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserDto join(UserJoinRequest userJoinRequest) {
        log.info("user 정보 : " + userJoinRequest.getUserName() + ", " + userJoinRequest.getEmail() + ", " + userJoinRequest.getPassword());

        //비즈니스 로직 - 회원가입
        //아이디가 중복이면 회원가입 x --> 예외 발생
        userRepository.findByUserName(userJoinRequest.getUserName()).ifPresent(
                user -> {throw new HospitalReviewAppException(
                        ErrorCode.DUPLICATED_USER_NAME, String.format("Username %s이 중복됩니다",userJoinRequest.getUserName())
                    );
                }
        );
        //중복이 아닐경우 회원가입
        //패스워드를 받아서 인코드 후 넣어준다
        User save = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));

        return UserDto.builder()
                .id(save.getId())
                .userName(save.getUserName())
                .email(save.getEmailAddress())
                .build();
    }

    public String login(String userName, String password) {
        //userName이 있는지 확인
        log.info("login userName : " + userName);
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> {throw new HospitalReviewAppException(
                        ErrorCode.NOT_FOUND, String.format("username %s이 없습니다", userName)
                );}
        );

        log.info("login password : " + password);
        //password일치하는지 확인
        if(!encoder.matches(password, user.getPassword())){
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, String.format("아이디 혹은 비밀번호가 잘못되었습니다"));
        }

        //두가지 확인중 예외가 없을경우 token발행


        return null;
    }
}
