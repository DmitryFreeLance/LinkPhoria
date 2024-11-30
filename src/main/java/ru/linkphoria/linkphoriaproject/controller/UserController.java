package ru.linkphoria.linkphoriaproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.linkphoria.linkphoriaproject.models.User;
import ru.linkphoria.linkphoriaproject.repositories.UserRepository;
import ru.linkphoria.linkphoriaproject.services.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

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
            return "redirect:/login";
        }

        return "main";
    }

    @GetMapping("/welcome")
    public String welcomePage(){
        return "welcome";
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email);

        if (user != null) {
            model.addAttribute("user", user);
        } else {
            return "redirect:/login";
        }

        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute("user") User user
    ) {

        String currentEmail = userDetails.getUsername();
        User userFromDB = userRepository.findByEmail(currentEmail);

        if (userFromDB != null) {
            userFromDB.setName(user.getName());
            userFromDB.setEmail(user.getEmail());

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                userFromDB.setPassword(encodedPassword);
            }

            userRepository.save(userFromDB);
        }

        return "redirect:/login";
    }
}