package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@Table(name = "user_hobby_link")
@Entity
public class UserHobbyLink {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;

        @Column(name = "experience")
        @Enumerated(EnumType.STRING)
        private Experience experience;

        @Column(name = "signup_date")
        @Temporal(TemporalType.DATE)
        private LocalDate signupDate;

        @ManyToOne
        private Hobby hobby;

        @ManyToOne
        private Users users;

    public UserHobbyLink(Experience experience, LocalDate signupDate) {
        this.experience = experience;
        this.signupDate = signupDate;
    }

    public UserHobbyLink(LocalDate signupDate, Hobby hobby, Experience experience, Users users) {
        this.signupDate = signupDate;
        this.hobby = hobby;
        this.experience = experience;
        this.users = users;
    }

    public void setUsers(Users user) {
        this.users = user;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public enum Experience {
            BEGINNER,
            INTERMEDIATE,
            ADVANCED,
            EXPERT,
            PROFESSIONAL
        }
}
