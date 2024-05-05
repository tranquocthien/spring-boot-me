package com.example.demo.database.entities;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressId")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subCategoryId")
    private SubCategory subCategory;

    private String description;

    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId")
    private User user;

    private boolean isAccept = false;

    private Timestamp createdAt;

    private String status;
    private int quantity;
    private String info;


    public Post() {
        super();
    }


    public Post( String title, Address address, SubCategory subCategory, String description, double price,
                User user, boolean isAccept, Timestamp createdAt) {
        super();
        this.title = title;
        this.address = address;
        this.subCategory = subCategory;
        this.description = description;
        this.price = price;
        this.user = user;
        this.isAccept = isAccept;
        this.createdAt = createdAt;
    }
}