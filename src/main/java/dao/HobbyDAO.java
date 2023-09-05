package dao;

import config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import model.Hobby;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class HobbyDAO {

    private static HobbyDAO instance;
    private static EntityManagerFactory emf;

    public static HobbyDAO getInstance() {
        if (instance == null) {
            emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");
            instance = new HobbyDAO();
        }
        return instance;
    }


    public List<User> findAllUsersWithGivenHoby(String hobby) {
        try (EntityManager em = emf.createEntityManager()){
            List<User> users = em.createQuery("SELECT u FROM Users u WHERE u.hobby.name = :hobby", User.class)
                    .setParameter("hobby", hobby)
                    .getResultList();
            return users;

        }

    }

}
