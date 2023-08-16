package com.gihae.filestorage.controller;

import com.gihae.filestorage.controller.dto.UserRequest;
import com.gihae.filestorage.core.security.JWTProvider;
import com.gihae.filestorage.core.utils.ApiUtils;
import com.gihae.filestorage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("users")
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
        userService.join(joinDTO);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginDTO", new UserRequest.LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserRequest.LoginDTO loginDTO){
        userService.login(loginDTO);
        return "redirect:/";
    }
}
