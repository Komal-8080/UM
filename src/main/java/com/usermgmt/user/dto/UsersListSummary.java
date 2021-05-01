package com.usermgmt.user.dto;

import com.usermgmt.user.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class UsersListSummary {

    private String profileImage;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailId;
    private LocalDate dateOfBirth;
    private boolean status;
    private String userRole;

    public UsersListSummary(User users) {
        this.profileImage = users.getProfileImage();
        this.firstName = users.getFirstName();
        this.middleName = users.getMiddleName();
        this.lastName = users.getLastName();
        this.emailId = users.getEmailId();
        this.dateOfBirth = users.getDateOfBirth();
        this.status = users.isStatus();
        this.userRole = users.getUserRole();
    }
}
