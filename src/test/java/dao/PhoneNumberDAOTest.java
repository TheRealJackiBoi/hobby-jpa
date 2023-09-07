package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import model.*;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneNumberDAOTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static PhoneNumberDAO phonenumberDAO;
    private static UserDAO userDAO;
    private static UsersPhoneNumberLinkDAO usersPhoneNumberLinkDAO;

    @BeforeAll
    static void settingUpFirst(){
        emf = HibernateConfigTest.getEntityManagerFactoryConfig("hobby_test");
        em = emf.createEntityManager();
        phonenumberDAO = PhoneNumberDAO.getInstance(emf);
        userDAO = UserDAO.getInstance(emf);
        usersPhoneNumberLinkDAO = UsersPhoneNumberLinkDAO.getInstance(emf);
    }
    @BeforeEach
    void setup(){

        Users testUser1 = new Users("testUser1", "testPassword1", null);
        Users testUser2 = new Users("testUser2", "testPassword1", null);
        Phonenumber testPhonenumber1 = new Phonenumber("+4521866999", Phonenumber.PhoneType.MOBILE);
        Phonenumber testPhonenumber2 = new Phonenumber("+4521866085", Phonenumber.PhoneType.MOBILE);
        Phonenumber testPhonenumber3 = new Phonenumber("+4521866089", Phonenumber.PhoneType.MOBILE);
        Phonenumber testPhonenumber4 = new Phonenumber("+4521866088", Phonenumber.PhoneType.MOBILE);

        phonenumberDAO.persistPhoneNumber(testPhonenumber1);
        phonenumberDAO.persistPhoneNumber(testPhonenumber2);
        phonenumberDAO.persistPhoneNumber(testPhonenumber3);
        phonenumberDAO.persistPhoneNumber(testPhonenumber4);

        userDAO.persistUser(testUser1);
        userDAO.persistUser(testUser2);

        UsersPhoneNumberLink usersPhoneNumberLink = new UsersPhoneNumberLink(testUser1, testPhonenumber1);
        UsersPhoneNumberLink usersPhoneNumberLink2 = new UsersPhoneNumberLink(testUser1, testPhonenumber2);
        UsersPhoneNumberLink usersPhoneNumberLink3 = new UsersPhoneNumberLink(testUser2, testPhonenumber3);
        UsersPhoneNumberLink usersPhoneNumberLink4 = new UsersPhoneNumberLink(testUser2, testPhonenumber4);


        usersPhoneNumberLinkDAO.persistUsersPhoneNumberLink(usersPhoneNumberLink);
        usersPhoneNumberLinkDAO.persistUsersPhoneNumberLink(usersPhoneNumberLink2);
        usersPhoneNumberLinkDAO.persistUsersPhoneNumberLink(usersPhoneNumberLink3);
        usersPhoneNumberLinkDAO.persistUsersPhoneNumberLink(usersPhoneNumberLink4);
    }

    @AfterEach
    void cleanUp(){
        em.getTransaction().begin();
        String sqlUserPhone = "TRUNCATE TABLE users_phonenumber RESTART IDENTITY CASCADE";
        Query userPhone = em.createNativeQuery(sqlUserPhone);
        userPhone.executeUpdate();

        String sqlUsers = "TRUNCATE TABLE users RESTART IDENTITY CASCADE";
        Query users = em.createNativeQuery(sqlUsers);
        users.executeUpdate();

        String sqlPhoneNumber = "TRUNCATE TABLE phonenumber RESTART IDENTITY CASCADE";
        Query phoneNumber = em.createNativeQuery(sqlPhoneNumber);
        phoneNumber.executeUpdate();
        em.getTransaction().commit();
    }

    @AfterAll
    static void closeDown(){
        emf.close();
    }


    @Test
    void getAllNumbersBelongingToAPerson() {
        Users userTest = userDAO.findUserById(1);

        List<Phonenumber> phonenumbers = phonenumberDAO.getAllNumbersBelongingToAPersonById(userTest.getId());
        assertEquals(2, phonenumbers.size());


    }
    @Test
    void findPhoneNumberByName() {

        List<Phonenumber> foundNumbers = phonenumberDAO.findPhoneNumberByName("testUser1");
        assertEquals("+4521866999", foundNumbers.get(0).getNumber());

    }
    @Test
    void findNumberByNumber(){

        Phonenumber foundNumber = phonenumberDAO.findPhoneNumberByNumber("+4521866085");
        Assert.assertEquals("+4521866085", foundNumber.getNumber());

    }

    @Test
    void deletePhoneNumberByNumber(){

        phonenumberDAO.deletePhoneNumberByNumber("+4521866088");
        Assert.assertNull(phonenumberDAO.findPhoneNumberByNumber("+4521866088"));


    }
    @Test
    void updatePhoneNumberByNumber(){
        Phonenumber testPhoneNumber = new Phonenumber("+4521866060", Phonenumber.PhoneType.MOBILE);
        Phonenumber newPhoneNumber = new Phonenumber("+4555555540", Phonenumber.PhoneType.MOBILE);

        phonenumberDAO.persistPhoneNumber(testPhoneNumber);

        Phonenumber updatedNumber = phonenumberDAO.updatePhoneNumberByNumber("+4521866060", newPhoneNumber);
        assertEquals("+4555555540", updatedNumber.getNumber());

    }
    @Test
    void persistPhoneNumber(){
        Phonenumber testPhoneNumber = new Phonenumber("+4521866081", Phonenumber.PhoneType.MOBILE);
        phonenumberDAO.persistPhoneNumber(testPhoneNumber);
        Assert.assertEquals(testPhoneNumber.getNumber(), phonenumberDAO.findPhoneNumberByNumber("+4521866081").getNumber());


    }

}