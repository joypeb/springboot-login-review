package com.hospital.review.service;

import com.hospital.review.domain.dto.UserDto;
import com.hospital.review.domain.dto.UserJoinRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    public UserDto join(UserJoinRequest userJoinRequest) {
        log.info("user 정보 : " + userJoinRequest.getUserName() + ", " + userJoinRequest.getEmail() + ", " + userJoinRequest.getPassword());
        return new UserDto("","","");
    }
}
