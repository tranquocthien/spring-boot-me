package com.example.demo.address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.database.entities.Address;


public interface AddressService {
    Page<Address> findAll(Pageable pageable);

    Optional<Address> findById(UUID id);

    Address save(Address p);

    void deleteById(UUID id);
}
