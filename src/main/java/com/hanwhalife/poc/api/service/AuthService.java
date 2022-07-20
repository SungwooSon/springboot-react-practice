package com.hanwhalife.poc.api.service;

import com.hanwhalife.poc.api.domain.Age;
import com.hanwhalife.poc.api.domain.Gender;
import com.hanwhalife.poc.api.domain.LoginHistory;
import com.hanwhalife.poc.api.domain.User;
import com.hanwhalife.poc.api.dto.CreateUserDto;
import com.hanwhalife.poc.api.dto.LoginDto;
import com.hanwhalife.poc.api.exception.UserNotFoundException;
import com.hanwhalife.poc.api.repository.LoginHistoryRepository;
import com.hanwhalife.poc.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final LoginHistoryRepository loginHistoryRepository;

    public void login(LoginDto loginDto, String loginIp) {
        User user = userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
                                  .orElseThrow(UserNotFoundException::new);

        loginHistoryRepository.save(LoginHistory.builder()
                                                .user(user)
                                                .loginIp(loginIp)
                                                .loginDateTime(LocalDateTime.now())
                                                .build());
    }

    public void signUp(CreateUserDto request) {

        //CreateUserDto -> User
        User user = User.builder()
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .username(request.getUsername())
                        .gender(Gender.getGenderByString(request.getGender()).orElseThrow()) // front에서 String으로 전달할텐데. String -> Gender 를 만들어주어야하는데
                        .age(new Age(request.getAge())) // int -> Age
                        //.age(Age::new)
                        .build();

        userRepository.save(user);
    }

}
