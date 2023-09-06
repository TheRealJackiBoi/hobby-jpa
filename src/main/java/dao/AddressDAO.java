package dao;

import com.beust.ah.A;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.TypedQuery;
import model.Address;
import model.City;
import model.Hobby;

import java.util.ArrayList;
import java.util.List;

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


    public void persistAddress(Address address){
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

    public List<Address> getAllAddresses(){
        List<Address> addressList;
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Address> typedQuery = em.createNamedQuery("Address.getAllAddresses", Address.class);
            addressList = typedQuery.getResultList();
            em.getTransaction().commit();
            return addressList;
        }
    }

    public Address checkIfAddressIsAlreadyThere(Address address){
        Address foundAddres;
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Address> typedQuery = em.createNamedQuery("Address.checkIfAddressExists", Address.class);
            foundAddres = typedQuery.setParameter(1, address.getStreetname()).setParameter(2,address.getHouseNumber()).setParameter(3,address.getFloor()).getSingleResult();
            em.getTransaction().commit();
            if(foundAddres == address){
                return foundAddres;
            }
        } catch (Exception e){
            throw new NoResultException();
        }
        return null;
    }
}
