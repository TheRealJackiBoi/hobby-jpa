package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "users")
@ToString
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

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<UserHobbyLink> userHobbyLinks = new HashSet<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<UsersPhoneNumberLink> usersPhoneNumberLinks = new HashSet<>();

    public Users(String name, String password, Address address) {
        this.name = name;
        this.password = password;
        this.address = address;
    }

    public void addUsersPhoneNumberLink(UsersPhoneNumberLink usersPhoneNumberLink) {
        usersPhoneNumberLinks.add(usersPhoneNumberLink);
    }

    public void removeUsersPhoneNumberLink(UsersPhoneNumberLink usersPhoneNumberLink) {
        usersPhoneNumberLinks.remove(usersPhoneNumberLink);
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
