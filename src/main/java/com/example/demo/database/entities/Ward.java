package com.example.demo.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Ward {
    @Id
    private int id;

    private String name;

    private String prefix;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")

    private City city;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")

    private District district;

    public Ward() {
        super();
    }

    public Ward(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

}