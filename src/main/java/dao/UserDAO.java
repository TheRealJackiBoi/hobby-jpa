package dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import model.Users;

public class UserDAO {

    //[US-1] As a user I want to get all the information about a person
    public Users retrieveAllUserInfo(Users users) {
        EntityManagerFactory emf = EMF.getInstance();
        try (var em = emf.createEntityManager()) {
            TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.id = :id", Users.class);
            query.setParameter("id", users.getId());
            return query.getSingleResult();
        }
    }
}