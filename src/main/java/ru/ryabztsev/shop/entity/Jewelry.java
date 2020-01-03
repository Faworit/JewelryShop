package ru.ryabztsev.shop.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Jewelry")
public class Jewelry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idJewelry;
    private String title;
    private String urlImage;
    private int price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "jewelry")
    private Set<Booking> bookings;

    public Jewelry() {
    }

    public Jewelry(String title, String urlImage, int price, Category category, Set<Booking> bookings) {
        this.title = title;
        this.urlImage = urlImage;
        this.price = price;
        this.category = category;
        this.bookings = bookings;
    }

    public Long getIdJewelry() {
        return idJewelry;
    }

    public void setIdJewelry(Long idJewelry) {
        this.idJewelry = idJewelry;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }
}
