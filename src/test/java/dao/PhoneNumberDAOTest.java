package dao;

import config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static model.UserHobbyLink.Experience.BEGINNER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PhoneNumberDAOTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static PhoneNumberDAO phonenumberDAO;

    @BeforeAll
    static void setup(){
        emf = HibernateConfig.getEntityManagerFactoryConfig("hobby_test");
        em = emf.createEntityManager();
        phonenumberDAO = PhoneNumberDAO.getInstance(emf);

    }
    @AfterAll
    static void closeDown(){
        emf.close();
    }


    @Test
    void getAllNumbersBelongingToAPerson() {
        Users testUser1 = new Users("testUser1", "testPassword1", null);
        Phonenumber testPhonenumber1 = new Phonenumber("12345678", Phonenumber.PhoneType.MOBILE);
        Phonenumber testPhonenumber2 = new Phonenumber("55555555", Phonenumber.PhoneType.MOBILE);

        PhoneNumberDAO phoneNumberDAO = PhoneNumberDAO.getInstance(EMF.getInstance("hobby_test"));
        UserDAO userDAO = UserDAO.getInstance(EMF.getInstance("hobby_test"));

        phoneNumberDAO.persistPhoneNumber(testPhonenumber1);
        phoneNumberDAO.persistPhoneNumber(testPhonenumber2);
        userDAO.persistUser(testUser1);

        testUser1.addPhonenumber(testPhonenumber1, Phonenumber.PhoneType.HOME);
        testUser1.addPhonenumber(testPhonenumber2, Phonenumber.PhoneType.WORK);



        List<Phonenumber> phonenumbers = phonenumberDAO.getAllNumbersBelongingToAPerson(testUser1.getId());
        assertEquals(2, phonenumbers.size());


    }
    @Test
    void findPhoneNumberByName() {
        Phonenumber testNumber = new Phonenumber("12345678", Phonenumber.PhoneType.MOBILE);
        Users testUser1 = new Users("testUser1", "testPassword1", null);

        PhoneNumberDAO phoneNumberDAO = PhoneNumberDAO.getInstance(EMF.getInstance("hobby_test"));
        UserDAO userDAO = UserDAO.getInstance(EMF.getInstance("hobby_test"));

        phoneNumberDAO.persistPhoneNumber(testNumber);
        userDAO.persistUser(testUser1);
        testUser1.addPhonenumber(testNumber, Phonenumber.PhoneType.MOBILE);

        Phonenumber foundNumber = phoneNumberDAO.findPhoneNumberByName("testUser1");
        assertEquals("12345678", foundNumber.getNumber());

    }
    @Test
    void findNumberByNumber(){
        Phonenumber testNumber = new Phonenumber("12345678", Phonenumber.PhoneType.MOBILE);
        PhoneNumberDAO phoneNumberDAO = PhoneNumberDAO.getInstance(EMF.getInstance("hobby_test"));
        phoneNumberDAO.persistPhoneNumber(testNumber);

        Phonenumber foundNumber = phonenumberDAO.findPhoneNumberByNumber("12345678");
        Assert.assertEquals("12345678", foundNumber.getNumber());

    }

    @Test
    void deletePhoneNumberByNumber(){
        Phonenumber testNumber = new Phonenumber("12345678", Phonenumber.PhoneType.MOBILE);
        Phonenumber newPhoneNumber = new Phonenumber("55555555", Phonenumber.PhoneType.MOBILE);

        PhoneNumberDAO phoneNumberDAO = PhoneNumberDAO.getInstance(EMF.getInstance("hobby_test"));
        phoneNumberDAO.persistPhoneNumber(testNumber);

        phonenumberDAO.deletePhoneNumberByNumber("12345678");
        Assert.assertNull(phonenumberDAO.findPhoneNumberByNumber("12345678"));


    }
    @Test
    void updatePhoneNumberByNumber(){
        Phonenumber testPhoneNumber = new Phonenumber("12345678", Phonenumber.PhoneType.MOBILE);
        Phonenumber newPhoneNumber = new Phonenumber("55555555", Phonenumber.PhoneType.MOBILE);

        PhoneNumberDAO phoneNumberDAO = PhoneNumberDAO.getInstance(EMF.getInstance("hobby_test"));
        phoneNumberDAO.persistPhoneNumber(testPhoneNumber);

        Phonenumber updatedNumber = phonenumberDAO.updatePhoneNumberByNumber("12345678", newPhoneNumber);
        assertEquals("55555555", updatedNumber.getNumber());

    }
    @Test
    void persistPhoneNumber(){
        Phonenumber testPhoneNumber = new Phonenumber("12345678", Phonenumber.PhoneType.MOBILE);
        phonenumberDAO.persistPhoneNumber(testPhoneNumber);
        Assert.assertEquals(testPhoneNumber.getNumber(), phonenumberDAO.findPhoneNumberByNumber("12345678").getNumber());


    }

}