package dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
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
