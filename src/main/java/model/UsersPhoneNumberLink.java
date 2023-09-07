package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "users_phonenumber")
@Entity
public class UsersPhoneNumberLink {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "phonenumber_id")
    private Phonenumber phonenumber;

    public UsersPhoneNumberLink(Users users, Phonenumber phonenumber) {
        this.users = users;
        this.phonenumber = phonenumber;
    }

    public void setUsersAndPhonenumber(Users users, Phonenumber phonenumber) {
        this.users = users;
        this.phonenumber = phonenumber;
        users.addUsersPhoneNumberLink(this);
        phonenumber.addUsersPhoneNumberLink(this);
    }

    public void removePhoneNumber() {
        this.phonenumber.removeUsersPhoneNumberLink(this);
        this.phonenumber = null;
    }

    public void removeUser() {
        this.users.removeUsersPhoneNumberLink(this);
        this.users = null;
    }


}
