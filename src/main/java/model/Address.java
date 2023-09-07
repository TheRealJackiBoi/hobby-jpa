package model;

import dao.HobbyDAO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "address")
@NamedQueries({
        @NamedQuery(name = "Address.getAllAddresses", query = "SELECT a FROM Address a"),
        @NamedQuery(name = "Address.checkIfAddressExists", query = "SELECT a FROM Address a WHERE a.streetname = ?1 AND a.houseNumber = ?2 AND a.floor = ?3")
})
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "streetname")
    private String streetname;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "floor")
    private String floor;

    @ManyToOne
    private City city;

    public Address(String streetname, String houseNumber, String floor, City city) {
        this.streetname = streetname;
        this.houseNumber = houseNumber;
        this.floor = floor;
        this.city = city;
    }

    @PrePersist
    void onPrePersist(){
        
    }
}
