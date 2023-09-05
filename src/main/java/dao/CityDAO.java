package dao;

import config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import model.Phonenumber;
import model.City;
import dao.CityZipDTO;
import java.util.List;

public class CityDAO {

    //singleton pattern
    private static CityDAO instance;
    private static EntityManagerFactory emf;

    private CityDAO() {
    }

    public static CityDAO getInstance() {
        if (instance == null) {
            emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");
            instance = new CityDAO();
        }
        return instance;
    }

    //US-7 As a user I want to get a list of all postcodes and cities in Denmark
    public List<CityZipDTO> getAllCitiesZipName() {
        try (var em = emf.createEntityManager()) {
             TypedQuery<CityZipDTO> q = em.createQuery("SELECT new dao.CityZipDTO(c.zip, c.name) FROM City c", CityZipDTO.class);
             List<CityZipDTO> cities = q.getResultList();
            return cities;
        }
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
