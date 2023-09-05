package dao;

<<<<<<< Updated upstream
import jakarta.persistence.TypedQuery;
import config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.Users;

import java.util.List;

public class UserDAO {

    private static UserDAO instance;
    private static EntityManagerFactory emf;

    private UserDAO() {
=======
import jakarta.persistence.EntityManagerFactory;
import model.Hobby;
import model.Users;

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
>>>>>>> Stashed changes
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");
            instance = new UserDAO();
        }
        return instance;
    }
  
  //[US-1] As a user I want to get all the information about a person
    public Users retrieveAllUserInfo(Users users) {
        try (var em = emf.createEntityManager()) {
            TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.id = :id", Users.class);
            query.setParameter("id", users.getId());
            return query.getSingleResult();
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