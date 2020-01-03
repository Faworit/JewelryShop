package ru.ryabztsev.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ryabztsev.shop.entity.Booking;
import ru.ryabztsev.shop.entity.Jewelry;
import ru.ryabztsev.shop.entity.User;
import ru.ryabztsev.shop.repository.BookingRepo;
import ru.ryabztsev.shop.repository.JewelryRepo;
import ru.ryabztsev.shop.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    JewelryRepo jewelryRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    BookingRepo bookingRepo;

    @GetMapping("/addBasket")
    public String makeOrder(@RequestParam Long jewelryId,
                            Model model,
                            Authentication authentication){
        User user = userRepo.findByUsername(authentication.getName());

        Jewelry jewelry;
        Optional<Jewelry> optJewelry = jewelryRepo.findById(jewelryId);
        if(optJewelry.isPresent()){
            jewelry = optJewelry.get();
            Booking booking = new Booking(user, jewelry);
            bookingRepo.save(booking);
            model.addAttribute("bookingId", booking.getId());
        }

        User user1 = userRepo.findByUsername(authentication.getName());

        List<Jewelry> goods = new ArrayList<>();
        for(Booking good : user1.getGoods()){
            System.out.println(1);
            goods.add(good.getJewelry());
        }
        model.addAttribute("goods", goods);
        return "Basket";
    }

    @PostMapping("/booking")
    public String buyGoods(@RequestParam Long idBooking,
                           Model model){
        bookingRepo.deleteById(idBooking);
        model.addAttribute("goods", jewelryRepo.findAll());
        return "greeting";
    }

    @GetMapping("/myBasket")
    public String getMyBasket(Authentication authentication, Model model){
        User user = userRepo.findByUsername(authentication.getName());
        Set<Booking> bookings = user.getGoods();
        List<Jewelry> goods = new ArrayList<>();
        for(Booking booking : bookings){
            Jewelry jewelry;
            Optional<Jewelry> optJewelry = jewelryRepo.findById(booking.getJewelry().getIdJewelry());
            if(optJewelry.isPresent()){
                jewelry = optJewelry.get();
                goods.add(jewelry);
            }
        }
        model.addAttribute("goods", goods);
        return "basket";
    }
}
