package com.example.citysmart.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.citysmart.repositories.CityRepository;
import com.example.citysmart.repositories.CountryRepository;
import com.example.citysmart.entities.City;
import com.example.citysmart.entities.Country;
import com.example.citysmart.pojos.CityRequest;


@Service
public class CityService {
    
    @Autowired CityRepository cityRepository;
    @Autowired CountryRepository countryRepository;

    public CityService(){

    }

    public List<City> getCities(){
        
        return cityRepository.findAll();
    }

    public City SaveCity(City city) {

       return cityRepository.save(city);
    }

    public City getCity(String cityname) {
        
        return cityRepository.findByCityname(cityname);
    }

    public City addCity1(CityRequest cityrequest) {
        Country  country = countryRepository.findById(cityrequest.countryid);
        City city = new City();
        city.setCityname(cityrequest.cityname);
        city.setCitycode(cityrequest.citycode);
        city.setCountry(country);
        return cityRepository.save(city);
    }

    public City addCity2(CityRequest cityrequest) {
        City city = new City();
        city.setCityname(cityrequest.cityname);
        city.setCitycode(cityrequest.citycode);
        city.setCountryid(cityrequest.countryid);
        return cityRepository.save(city);
    }

}


