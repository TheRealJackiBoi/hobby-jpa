package dao;

import config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static model.UserHobbyLink.Experience.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        City testCity = new City(2840, "Holte", "Sjælland", "Rudersdal Kommune");
        Address testAddress = new Address("TestStreet", "TestHouseNumber", "TestFloor", testCity);
        Users testUsers = new Users("TestUsername", "TestPassword", testAddress);
        Hobby testHobby = new Hobby("TestHobby", Hobby.HobbyType.INDOOR, "", "Generel");
        UserHobbyLink uhl = new UserHobbyLink(LocalDate.now(), testHobby, BEGINNER, testUsers);
        Phonenumber testPhonenumber = new Phonenumber("+4511223344", Phonenumber.PhoneType.MOBILE);

        UserDAO userDAO = UserDAO.getInstance(EMF.getInstance("hobby_test"));
        AddressDAO addressDAO = AddressDAO.getInstance(EMF.getInstance("hobby_test"));
        HobbyDAO hobbyDAO = HobbyDAO.getInstance(EMF.getInstance("hobby_test"));
        UserHobbyLinkDAO userHobbyLinkDAO = UserHobbyLinkDAO.getInstance(EMF.getInstance("hobby_test"));
        CityDAO cityDAO = CityDAO.getInstance(EMF.getInstance("hobby_test"));
        PhoneNumberDAO phoneNumberDAO = PhoneNumberDAO.getInstance(EMF.getInstance("hobby_test"));

        // Persist test entities
        testUsers.addPhonenumber(testPhonenumber, Phonenumber.PhoneType.WORK);

        phoneNumberDAO.persistPhoneNumber(testPhonenumber);
        cityDAO.persistCity(testCity);
        addressDAO.persistAddress(testAddress);
        userDAO.persistUser(testUsers);
        hobbyDAO.persistHobby(testHobby);
        userHobbyLinkDAO.persistUserHobbyLink(uhl);

        testUsers.addPhonenumber(testPhonenumber, Phonenumber.PhoneType.MOBILE);


        // Test method with test entities
        Users result = userDAO.retrieveAllUserInfo(testUsers);
        System.out.println(userDAO.retrieveAllUserInfo(testUsers));

        // Assert that the result is the same as the test entities, confirming that all the information is retrieved properly
        Assert.assertEquals(result.getName(), "TestUsername");

        Assert.assertEquals(result.getId(), testUsers.getId());

        Assert.assertEquals(result.getAddress().getStreetname(), testAddress.getStreetname());

    }

    @Test
    void numberOfPeopleWithHobby() {
        // Create test entities
        Hobby testHobby = new Hobby("TestHobby", Hobby.HobbyType.INDOOR, "", "Generel");
        Hobby testHobby2 = new Hobby("TestHobby2", Hobby.HobbyType.OUTDOOR, "", "Generel");

        Users user1 = new Users("TestUsername1", "TestPassword1", null);
        Users user2 = new Users("TestUsername2", "TestPassword2", null);
        Users user3 = new Users("TestUsername3", "TestPassword3", null);

        UserHobbyLink userHobbyLink1 = new UserHobbyLink(LocalDate.now(), testHobby, BEGINNER, user1);
        UserHobbyLink userHobbyLink2 = new UserHobbyLink(LocalDate.now(), testHobby, INTERMEDIATE, user2);
        UserHobbyLink userHobbyLink3 = new UserHobbyLink(LocalDate.now(), testHobby2, PROFESSIONAL, user3);

        UserDAO userDAO = UserDAO.getInstance(EMF.getInstance("hobby_test"));
        AddressDAO addressDAO = AddressDAO.getInstance(EMF.getInstance("hobby_test"));
        HobbyDAO hobbyDAO = HobbyDAO.getInstance(EMF.getInstance("hobby_test"));
        UserHobbyLinkDAO userHobbyLinkDAO = UserHobbyLinkDAO.getInstance(EMF.getInstance("hobby_test"));

        // Persist test entities
        userDAO.persistUser(user1);
        userDAO.persistUser(user2);
        userDAO.persistUser(user3);
        hobbyDAO.persistHobby(testHobby);
        hobbyDAO.persistHobby(testHobby2);
        userHobbyLinkDAO.persistUserHobbyLink(userHobbyLink1);
        userHobbyLinkDAO.persistUserHobbyLink(userHobbyLink2);
        userHobbyLinkDAO.persistUserHobbyLink(userHobbyLink3);

        // Test method with test entities
        Long result = userDAO.numberOfPeopleWithHobby("TestHobby");
        Long result2 = userDAO.numberOfPeopleWithHobby("TestHobby2");

        // Assert that the result is the same as the test entities, confirming that all the information is retrieved properly
        Assert.assertEquals(result, 2);
        Assert.assertNotEquals(result, 3);

        Assert.assertEquals(result2, 1);
        Assert.assertNotEquals(result2, 2);

    }

    @Test
    void getAllUsersFromSameZip() {
        //
        City holte = new City(2840, "Holte", "Sjælland", "Rudersdal Kommune");
        City lyngby = new City(2800, "Lyngby", "Sjælland", "Lyngby-Taarbæk Kommune");

        Address address1 = new Address("Rudeskovparken", "3", "2, 211", holte);
        Address address2 = new Address("Lyngby Hovedgade", "1", "", lyngby);
        Address address3 = new Address("Lyngby Hovedgade", "2", "", lyngby);

        Users user1 = new Users("TestUsername1", "TestPassword1", address1);
        Users user2 = new Users("TestUsername2", "TestPassword2", address2);
        Users user3 = new Users("TestUsername3", "TestPassword3", address3);

        UserDAO userDAO = UserDAO.getInstance(EMF.getInstance("hobby_test"));
        AddressDAO addressDAO = AddressDAO.getInstance(EMF.getInstance("hobby_test"));
        CityDAO cityDAO = CityDAO.getInstance(EMF.getInstance("hobby_test"));

        cityDAO.persistCity(holte);
        cityDAO.persistCity(lyngby);

        addressDAO.persistAddress(address1);
        addressDAO.persistAddress(address2);
        addressDAO.persistAddress(address3);

        userDAO.persistUser(user1);
        userDAO.persistUser(user2);
        userDAO.persistUser(user3);

        userDAO.getAllUsersFromSameZip("2800");
        assertEquals(userDAO.getAllUsersFromSameZip("2800").size(), 2);
    }

    @Test
    void retrieveAllUserInfoByPhoneNumber() {
        // Create test entities
        City testCity = new City(2840, "Holte", "Sjælland", "Rudersdal Kommune");
        Address testAddress = new Address("TestStreet", "TestHouseNumber", "TestFloor", testCity);
        Users testUsers = new Users("TestUsername", "TestPassword", testAddress);
        Hobby testHobby = new Hobby("TestHobby", Hobby.HobbyType.INDOOR, "", "Generel");
        UserHobbyLink uhl = new UserHobbyLink(LocalDate.now(), testHobby, BEGINNER, testUsers);
        Phonenumber testPhonenumber = new Phonenumber("+4511223344", Phonenumber.PhoneType.MOBILE);

        UserDAO userDAO = UserDAO.getInstance(EMF.getInstance("hobby_test"));
        AddressDAO addressDAO = AddressDAO.getInstance(EMF.getInstance("hobby_test"));
        HobbyDAO hobbyDAO = HobbyDAO.getInstance(EMF.getInstance("hobby_test"));
        UserHobbyLinkDAO userHobbyLinkDAO = UserHobbyLinkDAO.getInstance(EMF.getInstance("hobby_test"));
        CityDAO cityDAO = CityDAO.getInstance(EMF.getInstance("hobby_test"));
        PhoneNumberDAO phoneNumberDAO = PhoneNumberDAO.getInstance(EMF.getInstance("hobby_test"));

        // Persist test entities
        testUsers.addPhonenumber(testPhonenumber, Phonenumber.PhoneType.WORK);

        phoneNumberDAO.persistPhoneNumber(testPhonenumber);
        cityDAO.persistCity(testCity);
        addressDAO.persistAddress(testAddress);
        userDAO.persistUser(testUsers);
        hobbyDAO.persistHobby(testHobby);
        userHobbyLinkDAO.persistUserHobbyLink(uhl);

        // Test method with test entities
        System.out.println(userDAO.retrieveAllUserInfoByPhoneNumber("+4511223344").toString());
        Assert.assertTrue(testUsers.getPhonenumbers().contains(testPhonenumber));

    }
}