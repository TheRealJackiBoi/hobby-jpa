package dao;

import jakarta.persistence.EntityManagerFactory;
import model.City;
import model.Phonenumber;

public class CityDAO {

    private static EntityManagerFactory emf;
    private static CityDAO instance;

    public static CityDAO getInstance(EntityManagerFactory _emf){
        if(instance == null) {
            emf = _emf;
            instance = new CityDAO();
        }
        return instance;
    }

    public void persistCity(City city){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(city);
            em.getTransaction().commit();
        }
    }

    public City findCityByName(String cityName){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            City foundCity = em.find(City.class, cityName);
            em.getTransaction().commit();
            return foundCity;
        }
    }

    public void deleteCityByName(String cityName){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            City foundCity = findCityByName(cityName);
            em.remove(foundCity);
            em.getTransaction().commit();
        }
    }

    public City updateCityByCityName(String cityName, City newCity){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            City foundCity = findCityByName(cityName);
            City updatedCity = em.merge(newCity);
            em.getTransaction().commit();
            return updatedCity;
        }
    }


}
