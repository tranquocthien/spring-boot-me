package com.example.demo.database.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class District {
    @Id
    private int id;

    private String name;

    private String prefix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district", fetch = FetchType.EAGER)
    List<Ward> wards;


    public District() {
        super();
    }

    public District(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

}