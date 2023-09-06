package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "users")
    private Set<UserHobbyLink> userHobbyLinks = new HashSet<>();

    @ManyToMany
    private Set<Phonenumber> phonenumbers = new HashSet<>();

    public Users(String name, String password, Address address) {
        this.name = name;
        this.password = password;
        this.address = address;
    }

    public void addPhonenumber(Phonenumber phone) {
        phonenumbers.add(phone);
    }

    public void addHobby(Hobby hobby, UserHobbyLink.Experience experience) {
        LocalDate signupDate = LocalDate.now();
        userHobbyLinks.add(new UserHobbyLink(signupDate, hobby, experience, (this)));
    }
}
