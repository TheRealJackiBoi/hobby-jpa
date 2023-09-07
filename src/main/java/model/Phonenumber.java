package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "phonenumber")
@NamedQueries({
        @NamedQuery(name = "PhoneNumber.getAllUsersPhoneNumbers", query = "SELECT p FROM Phonenumber p JOIN users_phonenumber up ON up.users_id = ?1")
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

    @Override
    public String toString() {
        return "Phonenumber{" +
                "number='" + number + '\'' +
                ", type=" + type +
                ", users=" + users +
                '}';
    }
}
