package dao;

import config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.Users;

import java.util.List;

public class UserDAO {

    private static UserDAO instance;
    private static EntityManagerFactory emf;

    private UserDAO() {
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");
            instance = new UserDAO();
        }
        return instance;
    }

    public List<Users> getAllUsersFromSameZip(String zip) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Users> users = em.createQuery("SELECT u FROM Users u WHERE u.address.city.zip = :zip", Users.class)
                    .setParameter("zip", zip)
                    .getResultList();
            return users;
        }
    }

}
