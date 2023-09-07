package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Table(name = "address")
@ToString
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
}
