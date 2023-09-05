package test;

import dao.UserDAO;
import jakarta.persistence.TypedQuery;
import model.Address;
import model.City;
import model.Hobby;
import model.Users;
import org.testng.Assert;
import org.testng.annotations.Test;

import static model.UserHobbyLink.Experience.BEGINNER;

class UserDAOTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        EMFTest.getInstance();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        EMFTest.getInstance().close();
    }

    @org.junit.jupiter.api.Test
    @Test
    void retrieveAllUserInfo() {
        // Create test entities
        City testCity = new City(1000, "TestName", "TestRegion", "TestMunicipality");
        Address testAddress = new Address("TestStreet", "TestHouseNumber", "TestFloor", testCity);
        Users testUsers = new Users("TestName", "TestPassword", testAddress);
        Hobby testHobby = new Hobby("TestName",  Hobby.HobbyType.INDOOR, "","Generel");
        testUsers.addHobby(testHobby, BEGINNER);

        try (var em = EMFTest.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            em.persist(testCity);
            em.persist(testAddress);
            em.persist(testUsers);

            TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.id = :id", Users.class);
            query.setParameter("id", testUsers.getId());
            Users result = query.getSingleResult();

            em.getTransaction().commit();

            Assert.assertEquals(result.getName(), "TestName");
            Assert.assertEquals(result.getAddress(), testAddress);
            Assert.assertEquals(result.getAddress().getCity(), testCity);
        }
    }
}