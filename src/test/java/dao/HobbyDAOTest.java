package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.Hobby;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;


class HobbyDAOTest {

    private static EntityManagerFactory emf;
    private static HobbyDAO dao;
    private static EntityManager em;

    @BeforeAll
    static void setUp() {
        emf = HibernateConfigTest.getEntityManagerFactoryConfig("hobby_test");
        em = emf.createEntityManager();
        dao = HobbyDAO.getInstance(emf);

        Hobby h1 = new Hobby("skydning", Hobby.HobbyType.OUTDOOR, "wiki.com", "hurtigt");
        Hobby h2 = new Hobby("bueslåskamp", Hobby.HobbyType.OUTDOOR, "wiki.com", "hurtigt");
        Hobby h3 = new Hobby("dværgkast", Hobby.HobbyType.INDOOR, "wiki.com/sådanTrænerDuDinDværg", "joMindreJoBedre");

        dao.persistHobby(h1);
        dao.persistHobby(h2);
        dao.persistHobby(h3);

    }

    @Test
    void getAllHobbies() {
        Assert.assertEquals(3, dao.getAllHobbies().size());
    }

    @Test
    void getHobbiesWithNumberOfParticipants() {
        //doesnt work at the moment because it uses user_Hobby_link which is empty and there is no emf for it :)))
        System.out.println(dao.getHobbiesWithNumberOfParticipants());
    }

    @Test
    void findAllUsersWithGivenHoby() {

    }

    @Test
    void persistHobby() {
        Hobby h1 = new Hobby("søhesteridning", Hobby.HobbyType.INDOOR, "https://www.wikihow-fun.com/Train-Your-Dragon-(Fantasy-Play)", "fun");
        dao.persistHobby(h1);
        Assert.assertEquals(h1.getWikiLink(), dao.findHobbyByName("søhesteridning").getWikiLink());
    }

    @Test
    void findHobbyByName() {
        Assert.assertEquals("wiki.com/sådanTrænerDuDinDværg", dao.findHobbyByName("dværgkast").getWikiLink());
    }

    @Test
    void deleteHobbyByName() {
        dao.deleteHobbyByName("dværgkast");
        Assert.assertNull(dao.findHobbyByName("dværgkast"));
    }

    @Test
    void updateHobbyByHobbyName() {
        Hobby ridning = new Hobby("dværgkast", Hobby.HobbyType.OUTDOOR, "link removed due to not upholding community standards", "violent");

        dao.updateHobbyByHobbyName("dværgkast", ridning);

        Assert.assertEquals(ridning.getWikiLink(), dao.findHobbyByName("dværgkast").getWikiLink());

    }
}