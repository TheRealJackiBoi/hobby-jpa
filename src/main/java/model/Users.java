package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class Users {

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

    public Users(String name, String password, Address address) {
        this.name = name;
        this.password = password;
        this.address = address;
    }
}
