package com.hanwhalife.poc.api.service;

import com.hanwhalife.poc.api.domain.LoginHistory;
import com.hanwhalife.poc.api.domain.User;
import com.hanwhalife.poc.api.dto.UserResponse;
import com.hanwhalife.poc.api.exception.UserNotFoundException;
import com.hanwhalife.poc.api.repository.LoginHistoryRepository;
import com.hanwhalife.poc.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LoginHistoryRepository loginHistoryRepository;

    public List<UserResponse> getList() {
        /*return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());*/



        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {

            //LoginHistory loginHistory = loginHistoryRepository.findByUserId(user.getId());


            userResponses.add(
                UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .gender(user.getGender())
                        .married(user.getMarried())
                        .age(user.getAge())
                        .jobGroup(user.getJobGroup())
                        .jobPosition(user.getJobPosition())
                        .address(user.getAddress())
                        .firstLoginDateTime("1234")
                        .build()
            );
        }
        return userResponses;
    }
}
