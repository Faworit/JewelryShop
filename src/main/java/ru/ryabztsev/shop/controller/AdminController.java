package ru.ryabztsev.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.ryabztsev.shop.entity.Category;
import ru.ryabztsev.shop.entity.Jewelry;
import ru.ryabztsev.shop.entity.Role;
import ru.ryabztsev.shop.entity.User;
import ru.ryabztsev.shop.repository.CategoryRepo;
import ru.ryabztsev.shop.repository.JewelryRepo;
import ru.ryabztsev.shop.repository.UserRepo;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    private JewelryRepo jewelryRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/admin")
    public String administrationJewelry(Model model){
        model.addAttribute("goods", jewelryRepo.findAll());
        return "admin";
    }

    @GetMapping("/userList")
    public String userList(Model model){
        model.addAttribute("users", userRepo.findAll());
        return "userList";
    }

    @GetMapping("/editUser/{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "editUser";
    }

    @PostMapping("/editUser")
    public String editUser(@RequestParam String username,
                           @RequestParam Map<String, String> form,
                           @RequestParam("id") Long userID){

            Optional<User> optUser = userRepo.findById(userID);

            if(optUser.isPresent()){
                User user = optUser.get();
                user.setUsername(username);
                Set<String> roles = Arrays.stream(Role.values()).map(Role :: name).collect(Collectors.toSet());

                user.getRoles().clear();

                for(String key : form.keySet()){
                    if(roles.contains(key)){
                        user.getRoles().add(Role.valueOf(key));
                    }
                }
                userRepo.save(user);
            }
        return "redirect:/admin";
    }

    @GetMapping("/addJewelryForm")
    public String form(Model model){
        model.addAttribute("categories", categoryRepo.findAll());
        return "addJewelry";
    }

    @PostMapping("/addJewelry")
    public String addJewelry(@RequestParam String title,
                             @RequestParam int price,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam Long categoryId,
                             Model model) throws IOException {
        Jewelry jewelry = new Jewelry();
        jewelry.setTitle(title);
        Optional<Category> optCategory = categoryRepo.findById(categoryId);
        if(optCategory.isPresent()){
            Category category = optCategory.get();
            jewelry.setCategory(category);
        } else {
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("message", "choose category");
            return "addJewelry";
        }
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uidFile = UUID.randomUUID().toString();
            String resultFileName = uidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            jewelry.setUrlImage(resultFileName);
        }
        jewelry.setPrice(price);
        jewelryRepo.save(jewelry);

        model.addAttribute("goods", jewelryRepo.findAll());
        return "admin";
    }

    @GetMapping("/categoryList")
    public String categoryList(Model model){
        model.addAttribute("categories", categoryRepo.findAll());
        return "categoryList";
    }

    @GetMapping("/editCategory/{category}")
    public String editCategoryForm(@PathVariable Category category, Model model){
        model.addAttribute("category", category);
        return "editCategory";
    }

    @PostMapping("/editCategory")
    public String editCategory(@RequestParam String categoryName,
                               @RequestParam Long id,
                               Model model){
        Optional<Category> optCategory = categoryRepo.findById(id);
        if(optCategory.isPresent()){
            Category category = optCategory.get();
            category.setTitle(categoryName);
            categoryRepo.save(category);
        }
        model.addAttribute("categories", categoryRepo.findAll());
        return "redirect:/categoryList";
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam String nameCategory,
                              Model model){
        Category category = new Category();
        Category categoryFromDb = categoryRepo.findByTitle(nameCategory);
        if(categoryFromDb != null){
            model.addAttribute("message", "this category is exist");
            return "admin";
        }
        category.setTitle(nameCategory);
        categoryRepo.save(category);
        return "redirect:/categoryList";

    }


}
