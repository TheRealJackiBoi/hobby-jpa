package dao;

import config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

public final class EMF {
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getInstance(String dbName) {
        if (emf == null) {
           emf = HibernateConfig.getEntityManagerFactoryConfig(dbName);
        }
        return emf;
    }
}
