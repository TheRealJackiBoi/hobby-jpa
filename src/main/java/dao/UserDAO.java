package dao;

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