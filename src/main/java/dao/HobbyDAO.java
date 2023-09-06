package dao;


import jakarta.persistence.EntityManager;
import config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import model.Hobby;
import model.Users;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
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


    public List<Users> findAllUsersWithGivenHoby(String hobby) {
        try (EntityManager em = emf.createEntityManager()){
            List<Users> users = em.createQuery("SELECT u FROM Users u WHERE u.hobby.name = :hobby", Users.class)
                    .setParameter("hobby", hobby)
                    .getResultList();
            return users;

        }

    }

    public void persistHobby(Hobby hobby){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        }
    }

    public Hobby findHobbyByName(String name){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hobby foundHobby = em.find(Hobby.class, name);
            em.getTransaction().commit();
            return foundHobby;
        }
    }

    public void deleteHobbyByName(String hobbyname){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hobby foundHobby = findHobbyByName(hobbyname);
            em.remove(foundHobby);
            em.getTransaction().commit();
        }
    }

    public Hobby updateHobbyByHobbyName(String oldHobbyName, Hobby newHobby){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hobby foundhobby = findHobbyByName(oldHobbyName);
            Hobby updatedHobby= em.merge(newHobby);
            em.getTransaction().commit();
            return updatedHobby;
        }
    }

    //US-5 get a list of all the hobbies
    //TODO: test
    public List<Hobby> getAllHobbies(){
        List<Hobby> hobbyList;
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
