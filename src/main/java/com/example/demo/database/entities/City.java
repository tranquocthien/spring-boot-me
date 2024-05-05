package com.example.demo.database.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class City {
    @Id
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city", fetch = FetchType.EAGER)
    List<District> districs;

    public City() {
        super();
    }

    public City(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}