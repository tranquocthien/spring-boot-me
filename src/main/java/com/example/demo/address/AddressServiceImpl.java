package com.example.demo.address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.group4.entity.*;
import  com.example.demo.AddressRepository;
import com.group4.Service.*;


@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AdressRepository adressRepository;

    @Override
    public Optional<Address> findById(UUID id) {

        return adressRepository.findById(id);
    }

    @Override
    public Address save(Address p) {

        return adressRepository.save(p);
    }

    @Override
    public void deleteById(UUID id) {
        adressRepository.deleteById(id);

    }

    @Override
    public Page<Address> findAll(Pageable pageable) {

        return adressRepository.findAll(pageable);
    }

}
