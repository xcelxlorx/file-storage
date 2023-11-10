package com.gihae.filestorage.controller;

import com.gihae.filestorage.controller.dto.UserRequest;
import com.gihae.filestorage.controller.dto.UserResponse;
import com.gihae.filestorage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/join")
    public String joinForm(Model model){
        model.addAttribute("joinDTO", new UserRequest.JoinDTO());
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute UserRequest.JoinDTO joinDTO){
        log.info("회원가입");
        userService.join(joinDTO);
        log.info("회원가입 성공");
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginDTO", new UserRequest.LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserRequest.LoginDTO loginDTO){
        log.info("로그인");
        userService.login(loginDTO);
        log.info("로그인 성공");
        return "redirect:/";
    }

    @PostMapping("/logout")
    public void logout(){

    }

    @GetMapping("/my-page")
    public String myPage(Model model){
        Long userId = 1L;
        UserResponse.FindUserDTO user = userService.findById(userId);
        model.addAttribute("user", user);
        return "my-page";
    }
}
