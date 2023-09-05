package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "phonenumber")
@NamedQueries({
        @NamedQuery(name = "PhoneNumber.getAllUsersPhoneNumbers", query = "SELECT p FROM Phonenumber p WHERE Users = ?1")
})
@Entity
public class Phonenumber {

    @Id
    @Column(name = "number", nullable = false, unique = true)
    private String number;

    @Column(name = "type")
    private PhoneType type;

    @ManyToMany(mappedBy = "phonenumbers")
    private Set<Users> users;


    public Phonenumber(String number, PhoneType type) {
        this.number = number;
        this.type = type;
    }

    public enum PhoneType {
        HOME,
        WORK,
        MOBILE
    }
}
