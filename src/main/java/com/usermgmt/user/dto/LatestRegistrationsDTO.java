package com.usermgmt.user.dto;

import com.usermgmt.user.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class LatestRegistrationsDTO {

    private String name;
    private LocalDateTime registrationTime;

    public LatestRegistrationsDTO(User users) {
        this.name = users.getFirstName()+" " +users.getLastName();
        this.registrationTime = users.getRegistrationDate();
    }
}
