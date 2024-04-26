package ru.gb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gb.entity.User;
import ru.gb.service.UserService;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public String selectAllUsers(Model model){
        List<User> users = userService.selectAllUsers();
        model.addAttribute("users", users);

        return "user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user){
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(User user) {
        userService.insertUser(user);
        return "redirect:/users";
    }

    @GetMapping("/user-delete/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        userService.deleteUserById(id);

        return "redirect:/users";
    }

    @GetMapping("user-update/{id}")
    public String selectUserById(@PathVariable("id") Integer id, Model model){
        User user = userService.selectUserById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }
}
