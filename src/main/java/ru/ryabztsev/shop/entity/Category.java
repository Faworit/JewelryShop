package ru.ryabztsev.shop.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "category")
    private List<Jewelry> jewelries;

    public Category() {
    }

    public Category(String title, List<Jewelry> jewelries) {
        this.title = title;
        this.jewelries = jewelries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Jewelry> getJewelries() {
        return jewelries;
    }

    public void setJewelries(List<Jewelry> jewelries) {
        this.jewelries = jewelries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
