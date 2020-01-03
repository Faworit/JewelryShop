package ru.ryabztsev.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ryabztsev.shop.entity.Category;
import ru.ryabztsev.shop.entity.Jewelry;
import ru.ryabztsev.shop.entity.User;
import ru.ryabztsev.shop.repository.CategoryRepo;
import ru.ryabztsev.shop.repository.JewelryRepo;
import ru.ryabztsev.shop.repository.UserRepo;

@Controller
public class ShopController {
    @Autowired
    private JewelryRepo jewelryRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/")
    public String greeting(Model model){
        model.addAttribute("goods", jewelryRepo.findAll());

        return "greeting";
    }
    @GetMapping("/log")
    public String test(){
        return "login";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam String filter,
                         Model model) {
        if(filter.equals("show all")){
            model.addAttribute("goods", jewelryRepo.findAll());
            return "greeting";
        }
        Category category = categoryRepo.findByTitle(filter);
        model.addAttribute("goods", jewelryRepo.findAllByCategory(category));
        return "greeting";
    }
}
