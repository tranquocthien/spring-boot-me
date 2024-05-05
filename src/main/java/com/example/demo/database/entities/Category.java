package com.example.demo.database.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Category implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String photo;
    @OneToMany(cascade=CascadeType.ALL, mappedBy="category",fetch = FetchType.EAGER)
    private List<SubCategory> subcategories;

}