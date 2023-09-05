package dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import model.Hobby;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HobbyDAO {

    private static EntityManagerFactory emf;

    private static HobbyDAO instance;

    public static HobbyDAO getInstance(EntityManagerFactory _emf){
        if(instance == null) {
            emf = _emf;
            instance = new HobbyDAO();
        }
        return instance;
    }

    //US-5 get a list of all the hobbies
    //TODO: test
    public List<Hobby> getAllHobbies(){
        List<Hobby> hobbyList;
        EntityManagerFactory emf = EMF.getInstance();
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Hobby> typedQuery = em.createNamedQuery("Hobby.getAllHobbies", Hobby.class);
            hobbyList = typedQuery.getResultList();
            em.getTransaction().commit();
            return hobbyList;
        }
    }

    //US-5 COMPLETED
    //TODO: test
    public Map<Hobby, Integer> getHobbiesWithNumberOfParticipants(){
        Map<Hobby, Integer> hobbyIntegerMap = new HashMap<>();

        UserHobbyLinkDAO userHobbyLinkDAO = new UserHobbyLinkDAO();
        List<Hobby> hobbyList = getAllHobbies();
        for (Hobby h: hobbyList) {
            hobbyIntegerMap.put(h, userHobbyLinkDAO.countNumberOfParticipantsInHobby(h.getName()));
        }
        return hobbyIntegerMap;
    }


}
