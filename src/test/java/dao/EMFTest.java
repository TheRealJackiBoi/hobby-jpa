package dao;

import config.HibernateConfigTest;
import jakarta.persistence.EntityManagerFactory;

public final class EMFTest {
    private static final EntityManagerFactory emf = HibernateConfigTest.getEntityManagerFactoryConfig("hobby");

    public static EntityManagerFactory getInstance() {
        if (emf == null) {
            return HibernateConfigTest.getEntityManagerFactoryConfig("hobby_test");
        }
        return emf;
    }
}