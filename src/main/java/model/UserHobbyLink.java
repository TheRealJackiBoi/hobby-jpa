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
        private Users user;

    public UserHobbyLink(Experience experience) {
        this.experience = experience;
    }

    public enum Experience {
            BEGINNER,
            INTERMEDIATE,
            ADVANCED,
            EXPERT,
            PROFESSIONAL
        }

}
