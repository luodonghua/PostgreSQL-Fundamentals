package com.example.citysmart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.citysmart.entities.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>{
    Country findById(int id);
}
