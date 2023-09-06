package dao;

import lombok.Getter;
import lombok.ToString;
import model.Address;
import model.Phonenumber;
import model.UserHobbyLink;

import java.util.Set;

@ToString
@Getter
public class UsersNameAddressHobbiesNumbersDTO {
    private String name;
    private Address address;
    private Set<UserHobbyLink> hobbies;
    private Set<Phonenumber> phonenumbers;

    public UsersNameAddressHobbiesNumbersDTO(String name, Address address, Set<UserHobbyLink> hobbies, Set<Phonenumber> phonenumbers) {
        this.name = name;
        this.address = address;
        this.hobbies = hobbies;
        this.phonenumbers = phonenumbers;
    }

}
