package dao;

import jakarta.persistence.EntityManagerFactory;
import model.Users;
import model.UsersPhoneNumberLink;

public class UsersPhoneNumberLinkDAO {

    private static EntityManagerFactory emf;

    private static UsersPhoneNumberLinkDAO instance;

    public static UsersPhoneNumberLinkDAO getInstance(EntityManagerFactory _emf){
        if(instance == null) {
            emf = _emf;
            instance = new UsersPhoneNumberLinkDAO();
        }
        return instance;
    }

    public UsersPhoneNumberLink persistUsersPhoneNumberLink(UsersPhoneNumberLink usersPhoneNumberLink){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(usersPhoneNumberLink);
            em.getTransaction().commit();
            return usersPhoneNumberLink;
        }
    }

    public UsersPhoneNumberLink findUsersPhoneNumberLinkById(int id){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            UsersPhoneNumberLink foundUsersPhoneNumberLink = em.find(UsersPhoneNumberLink.class, id);
            em.getTransaction().commit();
            return foundUsersPhoneNumberLink;
        }
    }

    public void deleteUsersPhoneNumberLinkById(int id){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            UsersPhoneNumberLink foundUsersPhoneNumberLink = findUsersPhoneNumberLinkById(id);
            em.remove(foundUsersPhoneNumberLink);
            em.getTransaction().commit();
        }
    }

    public UsersPhoneNumberLink updateUsersPhoneNumberLinkById(int id, UsersPhoneNumberLink newUsersPhoneNumberLink){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            UsersPhoneNumberLink foundUsersPhoneNumberLink = findUsersPhoneNumberLinkById(id);
            foundUsersPhoneNumberLink.setUsersAndPhonenumber(newUsersPhoneNumberLink.getUsers(), newUsersPhoneNumberLink.getPhonenumber());
            em.getTransaction().commit();
            return foundUsersPhoneNumberLink;
        }
    }


}
