import config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.Address;
import model.City;
import model.Hobby;
import model.User;


public class Main {
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");


    public static void main(String[] args) {
        populate();
        emf.close();


    }

    private static void populate() {
        try (EntityManager em = emf.createEntityManager()) {
            City city = new City(2720, "vanløse", "københavn", "københavn");
            Address address = new Address("verasalle", "22", "1", city);
            em.getTransaction().begin();
            User u1 = new User("Egon", "1234");
            u1.setAddress(address);
            em.persist(city);
            em.persist(address);
            em.persist(u1);


            em.getTransaction().commit();

        }

    }


}