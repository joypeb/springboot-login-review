package com.hospital.review.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.review.HospitalLoginReviewApplication;
import com.hospital.review.domain.dto.UserDto;
import com.hospital.review.domain.dto.UserJoinRequest;
import com.hospital.review.exception.ErrorCode;
import com.hospital.review.exception.HospitalReviewAppException;
import com.hospital.review.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.Table;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    UserJoinRequest userJoinRequest = UserJoinRequest.builder()
            .userName("id1")
            .password("1234")
            .email("id1@naver.com")
            .build();

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void joinSuccess() throws Exception {

        when(userService.join(any())).thenReturn(mock(UserDto.class));

        mockMvc.perform(post("/api/v1/users/join").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패")
    @WithMockUser
    void joinFailed() throws Exception {

        when(userService.join(any())).thenThrow(new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(post("/api/v1/users/join").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("로그인 실패 - id없음")
    @WithMockUser
    void 로그인실패1() throws Exception {
        //무엇을 보내서 : id, pw
        String id = "id1";
        String password = "1234";

        when(userService.login(anyString(),anyString())).thenThrow(new HospitalReviewAppException(ErrorCode.NOT_FOUND, ""));
        //무엇을 받을까 : NOW_FOUND
        mockMvc.perform(post("/api/v1/users/login").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인 실패 - pwd잘못 입력")
    @WithMockUser
    void 로그인실패2() {

    }

    @Test
    @DisplayName("로그인 성공")
    @WithMockUser
    void 로그인성공() {

    }
}