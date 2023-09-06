package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password")
    private String password;

    @ManyToOne
    private Address address;

    @OneToMany(mappedBy = "user")
    private Set<UserHobbyLink> userHobbyLinks;

    @ManyToMany
    private Set<Phonenumber> phonenumbers;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
