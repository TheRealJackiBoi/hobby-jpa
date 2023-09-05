package dao;

import config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

public final class EMF {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");

    public static EntityManagerFactory getInstance() {
        if (emf == null) {
            return HibernateConfig.getEntityManagerFactoryConfig("hobby");
        }
        return emf;
    }
}