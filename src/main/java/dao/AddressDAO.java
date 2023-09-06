package dao;

import jakarta.persistence.EntityManagerFactory;
import model.Address;
import model.City;

public class AddressDAO {


    private static EntityManagerFactory emf;
    private static AddressDAO instance;

    public static AddressDAO getInstance(EntityManagerFactory _emf){
        if(instance == null) {
            emf = _emf;
            instance = new AddressDAO();
        }
        return instance;
    }

    public void persistAdress(Address address){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
        }
    }

    public Address findAddressByID(int id){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Address foundAddress = em.find(Address.class, id);
            em.getTransaction().commit();
            return foundAddress;
        }
    }

    public void deleteById(int id){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Address foundAddress = findAddressByID(id);
            em.remove(foundAddress);
            em.getTransaction().commit();
        }
    }

    public Address updateAddressById(int id, Address newAddress){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Address foundAddress = findAddressByID(id);
            Address updatedAddress = em.merge(newAddress);
            em.getTransaction().commit();
            return updatedAddress;
        }
    }

}
