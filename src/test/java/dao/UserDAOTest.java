package dao;

import dao.UserDAO;
import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.EMFTest;

import java.time.LocalDate;

import static model.UserHobbyLink.Experience.*;

class UserDAOTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        test.EMFTest.getInstance();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        test.EMFTest.getInstance().close();
    }

    @org.junit.jupiter.api.Test
    @Test
    void retrieveAllUserInfo() {
        // Create test entities
        City testCity = new City(1000, "TestCity", "TestRegion", "TestMunicipality");
        Address testAddress = new Address("TestStreet", "TestHouseNumber", "TestFloor", testCity);
        Users testUsers = new Users("TestUsername", "TestPassword", testAddress);
        Hobby testHobby = new Hobby("TestHobby",  Hobby.HobbyType.INDOOR, "","Generel");
        testUsers.addHobby(testHobby, BEGINNER);
        UserDAO userDAO = new UserDAO();

        // Perist test entities
        try (var em = test.EMFTest.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            em.persist(testCity);
            em.persist(testAddress);
            em.persist(testUsers);
            em.persist(testHobby);
            em.getTransaction().commit();

            // Test method with test entities
            Users result = userDAO.retrieveAllUserInfo(testUsers, test.EMFTest.getInstance());

            // Assert that the result is the same as the test entities, confirming that all the information is retrieved properly
            System.out.println(result.getName());
            System.out.println(testUsers.getName());
            Assert.assertEquals(result.getName(), "TestUsername");

            Assert.assertEquals(result.getId(), testUsers.getId());

            System.out.println(result.getAddress().getStreetname());
            System.out.println(testAddress.getStreetname());
            Assert.assertEquals(result.getAddress().getStreetname(), testAddress.getStreetname());

            System.out.println(result.getAddress().getCity().getName());
            System.out.println(testCity.getName());
            Assert.assertEquals(result.getAddress().getCity().getName(), testCity.getName());

        }
    }

    @org.junit.jupiter.api.Test
    @Test
    void numberOfPeopleWithHobby() {
        // Create test entities
        Users user1 = new Users("TestUsername1", "TestPassword1", null);
        Users user2 = new Users("TestUsername2", "TestPassword2", null);
        Users user3 = new Users("TestUsername3", "TestPassword3", null);

        Hobby testHobby = new Hobby("TestHobby",  Hobby.HobbyType.INDOOR, "","Generel");

        UserHobbyLink userHobbyLink1 = new UserHobbyLink(LocalDate.now(), testHobby, BEGINNER, user1);
        UserHobbyLink userHobbyLink2 = new UserHobbyLink(LocalDate.now(), testHobby, INTERMEDIATE, user2);
        UserHobbyLink userHobbyLink3 = new UserHobbyLink(LocalDate.now(), testHobby, PROFESSIONAL, user3);

        /*
        user1.addHobby(testHobby, BEGINNER);
        user2.addHobby(testHobby, INTERMEDIATE);
        user3.addHobby(testHobby, PROFESSIONAL);

         */

        UserDAO userDAO = new UserDAO();

        try(var em = test.EMFTest.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(testHobby);
            em.persist(userHobbyLink1);
            em.persist(userHobbyLink2);
            em.persist(userHobbyLink3);
            em.getTransaction().commit();

            // Test method with test entities
            int result = userDAO.numberOfPeopleWithHobby(testHobby, EMFTest.getInstance());

            // Assert that the result is the same as the test entities, confirming that all the information is retrieved properly
            System.out.println(user1.getUserHobbyLinks());
            Assert.assertEquals(result, 3);
        }
    }
}