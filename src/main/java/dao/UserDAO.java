package dao;

import jakarta.persistence.TypedQuery;
import config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.Hobby;
import model.UserHobbyLink;
import model.Users;

import java.util.List;

public class UserDAO {

    private static EntityManagerFactory emf;

    private static UserDAO instance;

    public static UserDAO getInstance(EntityManagerFactory _emf){
        if(instance == null) {
            emf = _emf;
            instance = new UserDAO();
        }
        return instance;
    }

    public void persistUser(Users users){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(users);
            em.getTransaction().commit();
        }
    }

    public Users findUserByName(String name){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Users foundUser = em.find(Users.class, name);
            em.getTransaction().commit();
            return foundUser;
        }
    }

    public void deleteUserByName(String username){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Users foundUser = findUserByName(username);
            em.remove(foundUser);
            em.getTransaction().commit();
        }
    }

    public Users updateUserByUserName(String userName, Users newUser){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Users foundUser = findUserByName(userName);
            Users updatedUser = em.merge(foundUser);
            em.getTransaction().commit();
            return updatedUser;
        }
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");
            instance = new UserDAO();
        }
        return instance;
    }
  
  //[US-1] As a user I want to get all the information about a person
    // TODO: Change this method to be similar to {@link #retrieveAllUserInfo(Users)}
    public List<UsersNameAddressHobbiesNumbersDTO> retrieveAllUserInfo(Users users) {
        try (var em = emf.createEntityManager()) {
            TypedQuery<UsersNameAddressHobbiesNumbersDTO> q = em.createQuery("SELECT new dao.UsersNameAddressHobbiesNumbersDTO(u.name," +
                    " u.address," +
                    " u.userHobbyLinks," +
                    " u.phonenumbers) " +
                    "FROM Users u " +
                    "WHERE u.id = :id", UsersNameAddressHobbiesNumbersDTO.class);
            q.setParameter("id", users.getId());
            return q.getResultList();
        }
    }

    //[US-4] As a user I want to get the number of people with a given hobby
    public int numberOfPeopleWithHobby(Hobby hobby) {
        try (var em = emf.createEntityManager()) {
            TypedQuery<UserHobbyLink> query = em.createQuery("SELECT COUNT(hobby) FROM UserHobbyLink l WHERE l.hobby.id = :hobby", UserHobbyLink.class);
            query.setParameter("hobby", hobby);
            return query.getResultList().size();
        }
    }

    public List<Users> getAllUsersFromSameZip(String zip) {
        try (var em = emf.createEntityManager()) {
            List<Users> users = em.createQuery("SELECT u FROM Users u WHERE u.address.city.zip = :zip", Users.class)
                    .setParameter("zip", zip)
                    .getResultList();
            return users;
        }
    }

    //[US-8] As a user I want to get all the information about a person given a phonenumber - address, hobbies, etc.
    public List<UsersNameAddressHobbiesNumbersDTO> retrieveAllUserInfoByPhoneNumber(String phoneNumber) {
        try (var em = emf.createEntityManager()) {
            TypedQuery<UsersNameAddressHobbiesNumbersDTO> q = em.createQuery("SELECT new dao.UsersNameAddressHobbiesNumbersDTO(u.name," +
                    " u.address," +
                    " u.userHobbyLinks," +
                    " u.phonenumbers) " +
                    "FROM Users u " +
                    "JOIN Phonenumber p where p.number = :phoneNumber", UsersNameAddressHobbiesNumbersDTO.class);
            q.setParameter("phoneNumber", phoneNumber);
            return q.getResultList();
        }
    }

}