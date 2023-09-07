package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "phonenumber")
@ToString
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

    @ToString.Exclude
    @ManyToMany(mappedBy = "phonenumbers", fetch = FetchType.EAGER)
    private Set<Users> users;

    @PrePersist
    public void prePersist() {
        if (type == null) {
            type = PhoneType.MOBILE;
        }

        //check danish phone number if not throw exception
        if (!number.matches("^(\\+45)?\\d{8}$")) {
            throw new IllegalArgumentException("Phone number must be 8 digits long and start with +45");
        }

    }

    @PreUpdate
    public void preUpdate() {
        if (type == null) {
            type = PhoneType.MOBILE;
        }

        //check danish phone number if not throw exception
        if (!number.matches("^(\\+45)?\\d{8}$")) {
            throw new IllegalArgumentException("Phone number must be 8 digits long and start with +45");
        }
    }

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
