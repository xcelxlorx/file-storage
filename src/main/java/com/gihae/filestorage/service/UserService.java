package com.gihae.filestorage.service;

import com.gihae.filestorage.controller.dto.UserRequest;
import com.gihae.filestorage.controller.dto.UserResponse;
import com.gihae.filestorage._core.errors.exception.Exception400;
import com.gihae.filestorage._core.errors.exception.Exception404;
import com.gihae.filestorage._core.security.JWTProvider;
import com.gihae.filestorage.domain.User;
import com.gihae.filestorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void join(UserRequest.JoinDTO joinDTO){
        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        User user = User.builder()
                .username(joinDTO.getUsername())
                .email(joinDTO.getEmail())
                .role("NORMAL")
                .password(joinDTO.getPassword())
                .build();
        userRepository.save(user);
    }

    public void login(UserRequest.LoginDTO loginDTO){
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new Exception400("이메일을 찾을 수 없습니다 : " + loginDTO.getEmail())
        );

        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new Exception400("패스워드가 잘못입력되었습니다 ");
        }

        JWTProvider.create(user);
    }

    public UserResponse.FindUserDTO findById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new Exception404("사용자를 찾을 수 없습니다.")
        );

        return new UserResponse.FindUserDTO(user);
    }
}
