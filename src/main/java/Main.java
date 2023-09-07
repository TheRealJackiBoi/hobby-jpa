import config.HibernateConfig;
import dao.EMF;
import dao.HobbyDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.*;

public class Main {


    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");
    public static void main(String[] args) {
        pupulate();
        emf.close();




    }

    private static void pupulate() {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            City city = new City(2720,"vanløse", "københavn", "københavn");
            Address address = new Address("verasalle", "22", "5", city);
            Users u1 = new Users("william", "1234", address);
            Users u2 = new Users("emil", "1234", address);

            Hobby h1 = new Hobby("fodbold", Hobby.HobbyType.INDOOR, "wiki", "f1");
            Hobby h2 = new Hobby("håndbold", Hobby.HobbyType.INDOOR, "wiki", "f2");




            em.persist(city);
            em.persist(address);
            em.persist(u1);
            em.persist(u2);
            em.persist(h1);
            em.persist(h2);


            em.getTransaction().commit();



        }

    }
}
