package com.example.demo.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private Ward ward;

    public Address() {
        super();
    }

}