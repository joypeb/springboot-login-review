package com.hospital.review.controller;

import com.hospital.review.domain.Response;
import com.hospital.review.domain.dto.*;
import com.hospital.review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) throws Throwable {
        UserDto userDto = userService.join(userJoinRequest);

        return Response.success(
                UserJoinResponse.builder().userName(userDto.getUserName()).email(userDto.getEmail()).build()
        );
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        String token = userService.login(userLoginRequest.getUserName(),userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
