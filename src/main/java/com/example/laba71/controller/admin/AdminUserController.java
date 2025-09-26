package com.example.laba71.controller.admin;

import com.example.laba71.model.RoleName;
import com.example.laba71.model.User;
import com.example.laba71.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", userRepo.findAll());
        return "admin/users/list";
    }

    @PostMapping("/admins")
    public String createAdmin(@RequestParam String name,
                              @RequestParam String passportNumber,
                              @RequestParam String password) {
        User u = new User();
        u.setName(name);
        u.setPassportNumber(passportNumber);
        u.setLibraryCardNumber(UUID.randomUUID().toString().substring(0, 10));
        u.setPassword(encoder.encode(password));
        u.setRoleName(RoleName.ROLE_ADMIN.name());
        u.setEnabled(true);
        userRepo.save(u);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/delete")
    public String usersDelete(@PathVariable Long id) {

        userRepo.deleteById(id);
        return "redirect:/admin/users";
    }
}