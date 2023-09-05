package dao;

import config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
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

}
