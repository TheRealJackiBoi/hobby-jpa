package test;
import config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

public final class EMFTest {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby_test");

    public static EntityManagerFactory getInstance() {
        if (emf == null) {
            return HibernateConfig.getEntityManagerFactoryConfig("hobby_test");
        }
        return emf;
    }
}
