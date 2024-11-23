package ru.linkphoria.linkphoriaproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.linkphoria.linkphoriaproject.models.User;
import ru.linkphoria.linkphoriaproject.repositories.UserRepository;
import ru.linkphoria.linkphoriaproject.services.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с таким email уже существует");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email);

        if (user != null) {
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("name", "Guest");
        }

        return "main";
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("name", "Guest");
        }

        return "profile";
    }
}
