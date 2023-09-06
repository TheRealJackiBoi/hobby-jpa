package dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import model.Hobby;
import model.UserHobbyLink;
import model.Users;

public class UserDAO {
    EntityManagerFactory emf = EMF.getInstance();

    //[US-1] As a user I want to get all the information about a person
    public Users retrieveAllUserInfo(Users users, EntityManagerFactory emf) {
        try (var em = emf.createEntityManager()) {
            TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.id = :id", Users.class);
            query.setParameter("id", users.getId());
            return query.getSingleResult();
        }
    }

    //[US-4] As a user I want to get the number of people with a given hobby
    public int numberOfPeopleWithHobby(Hobby hobby, EntityManagerFactory emf) {
        try (var em = emf.createEntityManager()) {
            TypedQuery<UserHobbyLink> query = em.createQuery("SELECT COUNT(users) FROM UserHobbyLink c WHERE c.hobby = :hobby", UserHobbyLink.class);
            query.setParameter("hobby", hobby);
            return query.getResultList().size();
        }
    }
}