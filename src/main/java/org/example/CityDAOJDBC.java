package org.example;

import connection.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAOJDBC implements CityDAO {
    @Override
    public City findById(int id) {
        City city = null;
        try(Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from city where id=?")) {
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    int cityId = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String countryCode = resultSet.getString(3);
                    String district = resultSet.getString(4);
                    int population = resultSet.getInt(5);
                    city = new City(cityId,name,countryCode,district,population);
                    System.out.println(city);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("city is not found by this id");
        }
        return city;
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> cityList = new ArrayList<>();
        try(Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from city where countrycode=?")) {
            preparedStatement.setString(1, code);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int cityId = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String countryCode = resultSet.getString(3);
                    String district = resultSet.getString(4);
                    int population = resultSet.getInt(5);
                   City city = new City(cityId,name,countryCode,district,population);
                    cityList.add(city);
                }
            }
        }catch (SQLException e) {
                e.printStackTrace();
            System.out.println("List of city is not found by this countrycode");
            }
        return cityList;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> cityList = new ArrayList<>();
        try(Connection connection = MySqlConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from city where name= ?")) {
            preparedStatement.setString(1,name);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int cityId = resultSet.getInt(1);
                    String cityname = resultSet.getString(2);
                    String countryCode = resultSet.getString(3);
                    String district = resultSet.getString(4);
                    int population = resultSet.getInt(5);
                    City city = new City(cityId,cityname,countryCode,district,population);
                    cityList.add(city);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("List of city is not found by this name");
        }
        return cityList ;
    }

    @Override
    public List<City> findAll() {
        List<City> cityList = new ArrayList<>();
        try ( Connection connection = MySqlConnection.getConnection();
              Statement statement = connection.createStatement()){
           try( ResultSet resultSet = statement.executeQuery("select * from city")){
              while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String countrycode = resultSet.getString(3);
                String district = resultSet.getString(4);
                 int population = resultSet.getInt(5);
                City city = new City(id,name,countrycode,district,population);
                cityList.add(city);
            }
           }
        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
        }
        return cityList ;
    }

    @Override
    public City add(City city) {
        //City city1 = new City("test","test","swe",123456);
        String query = "insert into city(name,countrycode,district,population)values(?, ?, ?, ?)";
        try(Connection connection = MySqlConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryCode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4,city.getPopulation());
           int rowsInserted =  preparedStatement.executeUpdate();
           if(rowsInserted > 0){
               System.out.println("successfully added the city");
           }
           try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
           if(generatedKeys.next()){
               int generatedCityId = generatedKeys.getInt(1);
               System.out.println("Generated city id ="+generatedCityId);

           }
           }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("city with this name is not added");
        }

        return city;
    }

    @Override
    public City update(City city) {
        String query = "update city set countrycode = ?,district = ?, population = ? where name like ?";
        try(Connection connection = MySqlConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
        preparedStatement.setString(1, city.getCountryCode());
        preparedStatement.setString(2, city.getDistrict());
        preparedStatement.setInt(3,city.getPopulation());
        preparedStatement.setString(4, city.getName());
        int rowUpdated = preparedStatement.executeUpdate();
        if(rowUpdated > 0){
            System.out.println("city updated successfully");
        }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("city is not updated");
        }
        return city;
    }

    @Override
    public int delete(City city) {
        String query = "delete from city where name like ?";
        try(Connection connection = MySqlConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, city.getName());
             int rowDeleted = preparedStatement.executeUpdate();
            if(rowDeleted > 0){
                System.out.println("successfully deleted");
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("city deleted successfully");
        }
        return -1;
    }
}

