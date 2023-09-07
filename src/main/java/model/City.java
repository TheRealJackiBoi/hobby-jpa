package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "city")
@ToString
@Entity
public class City {

    @Id
    @Column(name = "zip", nullable = false, unique = true)
    private int zip;

    @Column(name = "name")
    private String name;

    @Column(name = "region")
    private String region;

    @Column(name = "municipality")
    private String municipality;

    @ToString.Exclude
    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
    private Set<Address> addresses;

    public City(int zip, String name, String region, String municipality) {
        this.zip = zip;
        this.name = name;
        this.region = region;
        this.municipality = municipality;
    }
}
