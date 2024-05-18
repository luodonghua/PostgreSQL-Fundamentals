package com.example.citysmart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.citysmart.entities.City;
import com.example.citysmart.pojos.CityRequest;
import com.example.citysmart.services.CityService;

@RestController
public class CityController {
    
    @Autowired
    CityService cityService;

    @GetMapping("getcities")
    public List<City> getCities(){
        return cityService.getCities();
    }

    @PostMapping("addcity")
    public City SaveCity (@RequestBody City city){
        return cityService.SaveCity(city);
    } // @RequestBody is used to convert the json data into java object (City)

    @GetMapping("getcity")
    public City getCity(String cityname){
        return cityService.getCity(cityname);
    }

    @PostMapping("savecity1")
    public City addCity1(@RequestBody CityRequest cityrequest){
        return cityService.addCity1(cityrequest);
    }

    @PostMapping("savecity2")
    public City addCity2(@RequestBody CityRequest cityrequest){
        return cityService.addCity2(cityrequest);
    }
}


