package com.stan.dubbobaseinterface.service;


import com.stan.dubbobaseinterface.entity.City;

public interface CityService {
    City findCityByName(String cityName);
}
