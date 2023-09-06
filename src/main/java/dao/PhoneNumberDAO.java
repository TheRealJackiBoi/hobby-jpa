package dao;

import config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import model.Phonenumber;
import model.Users;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberDAO {


    private static EntityManagerFactory emf;

    private static PhoneNumberDAO instance;

    public static PhoneNumberDAO getInstance(EntityManagerFactory _emf){
        if(instance == null) {
            emf = _emf;
            instance = new PhoneNumberDAO();
        }
        return instance;
    }

    public void persistPhoneNumber(Phonenumber phonenumber){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(phonenumber);
            em.getTransaction().commit();
        }
    }

    public Phonenumber findPhoneNumberByName(String number){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Phonenumber foundPhoneNumber = em.find(Phonenumber.class, number);
            em.getTransaction().commit();
            return foundPhoneNumber;
        }
    }

    public void deletePhoneNumberByNumber(String number){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Phonenumber foundPhoneNumber = findPhoneNumberByName(number);
            em.remove(foundPhoneNumber);
            em.getTransaction().commit();
        }
    }

    public Phonenumber updatePhoneNumberByNumber(String number, Phonenumber newPhoneNumber){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Phonenumber foundNumber = findPhoneNumberByName(number);
            Phonenumber updatedPhoneNumber = em.merge(newPhoneNumber);
            em.getTransaction().commit();
            return updatedPhoneNumber;
        }
    }

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
