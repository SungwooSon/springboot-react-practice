package com.hanwhalife.poc.api.service;

import com.hanwhalife.poc.api.dto.LoginDto;
import com.hanwhalife.poc.api.exception.UserNotFoundException;
import com.hanwhalife.poc.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    public void login(LoginDto loginDto) {
        userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
                        .orElseThrow(UserNotFoundException::new);

        // 전체 회원을 다 뒤져서, loginDto와 일치하는 findFirst. 하는 방법도 있겠지
    }

}
