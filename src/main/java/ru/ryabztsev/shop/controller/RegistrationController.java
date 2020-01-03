package ru.ryabztsev.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ryabztsev.shop.entity.Role;
import ru.ryabztsev.shop.entity.User;
import ru.ryabztsev.shop.repository.UserRepo;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        userRepo.save(user);

        return "redirect:/login";
    }
    /*public String addUser(@RequestParam String username,
                          @RequestParam String password
                          ){
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.USER);
        User user = new User(username, password, roles);
        System.out.printf("-------------------------------------------");
        System.out.println(user);
        userRepo.save(user);
        return "redirect:/login";
    }*/

}
