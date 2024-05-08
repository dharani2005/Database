package org.example;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        CityDAOJDBC cityDAOJDBC = new CityDAOJDBC();
        cityDAOJDBC.findById(1);
        List<City> cityList1 = cityDAOJDBC.findByCode("NLD");
        cityList1.forEach(System.out::println);
        List<City> cityList3 = cityDAOJDBC.findByName("Tilburg");
        cityList3.forEach(System.out::println);
        List<City> cityList2 = cityDAOJDBC.findAll();
        cityList2.forEach(System.out::println);
        City city = new City("test", "AFG", "swe", 123456);
        System.out.println(cityDAOJDBC.add(city));
        City city1 = new City("test", "swe", "kabool", 123456);
        System.out.println(cityDAOJDBC.update(city1));
        City city2 = new City("test", "swe", "kabool", 123456);
        System.out.println(cityDAOJDBC.delete(city2));


    }
}
