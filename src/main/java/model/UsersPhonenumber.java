package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "users_phonenumber")
@Entity
public class UsersPhonenumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "phonenumbers_number")
    private String phonenumber_number;

    @ManyToOne
    private Phonenumber phonenumber;

    @ManyToOne
    private Users users;

    public UsersPhonenumber(String phonenumber_number) {
        this.phonenumber_number = phonenumber_number;
    }

    public UsersPhonenumber(String phonenumber_number, Phonenumber phonenumber, Users users) {
        this.phonenumber_number = phonenumber_number;
        this.phonenumber = phonenumber;
        this.users = users;
    }

    public void setUsers(Users user) {
        this.users = user;
    }

    public void setPhonenumber(Phonenumber phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return "UsersPhonenumber{" +
                "id=" + id +
                ", phonenumber_number='" + phonenumber_number + '\'' +
                ", phonenumber=" + phonenumber +
                ", users=" + users +
                '}';
    }
}
