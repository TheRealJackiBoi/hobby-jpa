package dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.Hobby;
import model.UserHobbyLink;

import java.util.List;

public class UserHobbyLinkDAO {

    private static EntityManagerFactory emf;

    private static UserHobbyLinkDAO instance;

    public static UserHobbyLinkDAO getInstance(EntityManagerFactory _emf){
        if(instance == null) {
            emf = _emf;
            instance = new UserHobbyLinkDAO();
        }
        return instance;
    }

    public void persistUserHobbyLink(UserHobbyLink userHobbyLink){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(userHobbyLink);
            em.getTransaction().commit();
        }
    }

    public UserHobbyLink findUserHobbyLinkByUserName(String userName){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            UserHobbyLink foundLink = em.find(UserHobbyLink.class, userName);
            em.getTransaction().commit();
            return foundLink;
        }
    }

    public void deleteUserHobbyLinkByUserName(String userName){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            UserHobbyLink foundUser = findUserHobbyLinkByUserName(userName);
            em.remove(foundUser);
            em.getTransaction().commit();
        }
    }

    public UserHobbyLink updateUserHobbyLinkByUserName(String username, UserHobbyLink newUserHobbyLink){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            UserHobbyLink foundUserHobbyLink = findUserHobbyLinkByUserName(username);
            UserHobbyLink updatedUserHobbyLink= em.merge(newUserHobbyLink);
            em.getTransaction().commit();
            return updatedUserHobbyLink;
        }
    }

    //US-5 Find out how many are attending each hobby
    //TODO: test
    public int countNumberOfParticipantsInHobby(String hobbyName){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT count(b.user) FROM UserHobbyLink b WHERE UserHobbyLink.hobby = ?1");
            query.setParameter(1, hobbyName);
            em.getTransaction().commit();
            return query.getFirstResult();
        }
    }

}
