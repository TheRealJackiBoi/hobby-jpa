package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "hobby")
@Entity
public class Hobby {

    @Id
    @Column(name = "hobby")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private HobbyType type;

    @Column(name = "wiki_link")
    private String wikiLink;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "hobby")
    private Set<UserHobbyLink> userHobbyLinks;

    public Hobby(String name, HobbyType type, String wikiLink, String category) {
        this.name = name;
        this.type = type;
        this.wikiLink = wikiLink;
        this.category = category;
    }

    public enum HobbyType {
        INDOOR,
        OUTDOOR
    }

}
