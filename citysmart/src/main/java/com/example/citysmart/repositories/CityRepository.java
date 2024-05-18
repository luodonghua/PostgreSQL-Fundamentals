package com.example.citysmart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.citysmart.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City,Long>{

    City findByCityname(String cityname);
}

