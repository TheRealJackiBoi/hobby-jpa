package dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import model.Phonenumber;
import model.Users;
import model.UsersPhoneNumberLink;

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

    public Phonenumber findPhoneNumberByNumber(String number){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Phonenumber foundPhoneNumber = em.find(Phonenumber.class, number);
            em.getTransaction().commit();
            return foundPhoneNumber;
        }
    }
    public List<Phonenumber> findPhoneNumberByName(String name){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Phonenumber> tq = em.createQuery("SELECT p FROM Phonenumber p JOIN UsersPhoneNumberLink up ON up.phonenumber.number = p.number JOIN Users u ON u.id = up.users.id WHERE u.name = :name", Phonenumber.class);
            List<Phonenumber> foundPhoneNumber = tq.setParameter("name", name).getResultList();
            em.getTransaction().commit();
            return foundPhoneNumber;
        }
    }

    public void deletePhoneNumberByNumber(String number){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Phonenumber foundPhoneNumber = findPhoneNumberByNumber(number);
            for (UsersPhoneNumberLink usersPhoneNumberLink:
                 foundPhoneNumber.getUsersPhoneNumberLinks()) {
                usersPhoneNumberLink.removePhoneNumber();
                em.merge(usersPhoneNumberLink);
            }
            em.getTransaction().commit();
            em.getTransaction().begin();
            foundPhoneNumber = em.find(Phonenumber.class, number);
            em.remove(foundPhoneNumber);
            em.getTransaction().commit();
        }
    }

    public Phonenumber updatePhoneNumberByNumber(String number, Phonenumber newPhoneNumber){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Phonenumber foundNumber = findPhoneNumberByNumber(number);
            Phonenumber updatedPhoneNumber = em.merge(newPhoneNumber);
            em.getTransaction().commit();
            return updatedPhoneNumber;
        }
    }

    //US-2 - As a user i want to get all the phone numbers from a given person
    //TODO: test
    public List<Phonenumber> getAllNumbersBelongingToAPersonById(int id){
        List<Phonenumber> phonenumbers = new ArrayList<>();
        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TypedQuery<Phonenumber> typedQuery = em.createNamedQuery("PhoneNumber.getAllUsersPhoneNumbers", Phonenumber.class);
            phonenumbers = typedQuery.setParameter(1, id).getResultList();
            em.getTransaction().commit();
            return phonenumbers;
        }
    }

}
