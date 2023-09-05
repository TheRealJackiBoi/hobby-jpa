package dao;

import config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import model.Phonenumber;
import model.Users;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberDAO {


    //US-2 - As a user i want to get all the phone numbers from a given person
    //TODO: test
    public List<Phonenumber> getAllNumbersBelongingToAPerson(int id){
        List<Phonenumber> phonenumbers = new ArrayList<>();
        EntityManagerFactory emf = EMF.getInstance();
        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Users users = em.find(Users.class, id);
            TypedQuery<Phonenumber> typedQuery = em.createNamedQuery("PhoneNumber.getAllUsersPhoneNumbers", Phonenumber.class);
            phonenumbers = typedQuery.setParameter(1, users).getResultList();
            em.getTransaction().commit();
            return phonenumbers;
        }
    }

}
