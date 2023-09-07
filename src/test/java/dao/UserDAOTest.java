package dao;

import config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static model.UserHobbyLink.Experience.*;

public class UserDAOTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    static void init() {
        emf = HibernateConfig.getEntityManagerFactoryConfig("hobby_test");
        em = emf.createEntityManager();
    }

    @AfterAll
    static void tearDown() {
        emf.close();
    }

    @Test
    void retrieveAllUserInfo() {
        // Create test entities
        Address testAddress = new Address("TestStreet", "TestHouseNumber", "TestFloor", null);
        Users testUsers = new Users("TestUsername", "TestPassword", testAddress);
        Hobby testHobby = new Hobby("TestHobby", Hobby.HobbyType.INDOOR, "", "Generel");
        UserHobbyLink uhl = new UserHobbyLink(LocalDate.now(), testHobby, BEGINNER, testUsers);
        Phonenumber testPhonenumber = new Phonenumber("1122334455", Phonenumber.PhoneType.MOBILE);

        UserDAO userDAO = UserDAO.getInstance(EMF.getInstance("hobby_test"));
        AddressDAO addressDAO = AddressDAO.getInstance(EMF.getInstance("hobby_test"));
        HobbyDAO hobbyDAO = HobbyDAO.getInstance(EMF.getInstance("hobby_test"));
        UserHobbyLinkDAO userHobbyLinkDAO = UserHobbyLinkDAO.getInstance(EMF.getInstance("hobby_test"));

        // Persist test entities
        addressDAO.persistAddress(testAddress);
        userDAO.persistUser(testUsers);
        hobbyDAO.persistHobby(testHobby);
        userHobbyLinkDAO.persistUserHobbyLink(uhl);

        testUsers.addPhonenumber(testPhonenumber, Phonenumber.PhoneType.MOBILE);


        // Test method with test entities
        System.out.println(userDAO.retrieveAllUserInfo(testUsers));

        // Assert that the result is the same as the test entities, confirming that all the information is retrieved properly
        /*
        System.out.println(result.getName());
        System.out.println(testUsers.getName());
        Assert.assertEquals(result.getName(), "TestUsername");

        Assert.assertEquals(result.getId(), testUsers.getId());

        System.out.println(result.getAddress().getStreetname());
        System.out.println(testAddress.getStreetname());
        Assert.assertEquals(result.getAddress().getStreetname(), testAddress.getStreetname());

         */
    }

    @Test
    void numberOfPeopleWithHobby() {
        // Create test entities


        Hobby testHobby = new Hobby("TestHobby", Hobby.HobbyType.INDOOR, "", "Generel");


        Users user1 = new Users("TestUsername1", "TestPassword1", null);
        Users user2 = new Users("TestUsername2", "TestPassword2", null);
        Users user3 = new Users("TestUsername3", "TestPassword3", null);


        UserHobbyLink userHobbyLink1 = new UserHobbyLink(LocalDate.now(), testHobby, BEGINNER, user1);
        UserHobbyLink userHobbyLink2 = new UserHobbyLink(LocalDate.now(), testHobby, INTERMEDIATE, user2);
        UserHobbyLink userHobbyLink3 = new UserHobbyLink(LocalDate.now(), testHobby, PROFESSIONAL, user3);

        UserDAO userDAO = UserDAO.getInstance(EMF.getInstance("hobby_test"));
        AddressDAO addressDAO = AddressDAO.getInstance(EMF.getInstance("hobby_test"));
        HobbyDAO hobbyDAO = HobbyDAO.getInstance(EMF.getInstance("hobby_test"));
        UserHobbyLinkDAO userHobbyLinkDAO = UserHobbyLinkDAO.getInstance(EMF.getInstance("hobby_test"));

        // Persist test entities
        userDAO.persistUser(user1);
        userDAO.persistUser(user2);
        userDAO.persistUser(user3);
        hobbyDAO.persistHobby(testHobby);
        userHobbyLinkDAO.persistUserHobbyLink(userHobbyLink1);
        userHobbyLinkDAO.persistUserHobbyLink(userHobbyLink2);
        userHobbyLinkDAO.persistUserHobbyLink(userHobbyLink3);

        // Test method with test entities
        int result = userDAO.numberOfPeopleWithHobby(testHobby);

        // Assert that the result is the same as the test entities, confirming that all the information is retrieved properly
        System.out.println(user1.getUserHobbyLinks());
        Assert.assertEquals(result, 3);

    }
}