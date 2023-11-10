package com.gihae.filestorage.controller;

import com.gihae.filestorage._core.security.CustomUserDetails;
import com.gihae.filestorage.controller.dto.UserRequest;
import com.gihae.filestorage.controller.dto.UserResponse;
import com.gihae.filestorage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        userService.join(joinDTO);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginDTO", new UserRequest.LoginDTO());
        return "login";
    }

    @PostMapping("/logout")
    public void logout(){

    }

    @GetMapping("/my-page")
    public String myPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails){
        UserResponse.FindUserDTO user = userService.findById(userDetails.user().getId());
        model.addAttribute("user", user);
        return "my-page";
    }
}
