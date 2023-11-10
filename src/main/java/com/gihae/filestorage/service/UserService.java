package com.gihae.filestorage.service;

import com.gihae.filestorage._core.errors.exception.Exception404;
import com.gihae.filestorage.controller.dto.UserRequest;
import com.gihae.filestorage.controller.dto.UserResponse;
import com.gihae.filestorage.domain.Folder;
import com.gihae.filestorage.domain.User;
import com.gihae.filestorage.repository.FolderRepository;
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
    private final FolderRepository folderRepository;

    @Transactional
    public void join(UserRequest.JoinDTO joinDTO){
        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        save(joinDTO);
    }

    private void save(UserRequest.JoinDTO joinDTO) {
        User user = User.builder()
                .username(joinDTO.getUsername())
                .email(joinDTO.getEmail())
                .role("NORMAL")
                .password(joinDTO.getPassword())
                .usage(0L)
                .build();

        userRepository.save(user);

        Folder folder = Folder.builder()
                .name("root")
                .parent(null)
                .build();

        folderRepository.save(folder);
    }

    public UserResponse.FindUserDTO findById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new Exception404("사용자를 찾을 수 없습니다.")
        );

        return new UserResponse.FindUserDTO(user);
    }
}
