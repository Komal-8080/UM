package com.usermgmt.user.model;

import com.usermgmt.user.dto.RegistrationDTO;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.security.Permission;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "registration_details")
public @Data class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "userId")
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String country;
    private String mobileNumber;
    private String emailId;
    private String address;
    private String userName;
    private String password;
    private String confirmPassword;
    private String userRole;
    private String profileImage;
    private LocalDateTime lastLoginDateTime;
    private LocalDateTime loginDateTime;
    private boolean isVerify;
    private LocalDateTime registrationDate = LocalDateTime.now();
    private boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    private UserPermissions DashBoardPermissions;

    @OneToOne(cascade = CascadeType.ALL)
    private UserPermissions SettingsPermissions;

    @OneToOne(cascade = CascadeType.ALL)
    private UserPermissions UsersInformationPermissions;

    @OneToOne(cascade = CascadeType.ALL)
    private UserPermissions WebPage1Permissions;

    @OneToOne(cascade = CascadeType.ALL)
    private UserPermissions WebPage2Permissions;

    @OneToOne(cascade = CascadeType.ALL)
    private UserPermissions WebPage3Permissions;

    public User(RegistrationDTO registrationDTO) {
        this.updateUserDetails(registrationDTO);
    }

    public User() { }

    public void updateUserDetails(RegistrationDTO registrationDTO) {
        this.firstName = registrationDTO.getFirstName();
        this.middleName = registrationDTO.getMiddleName();
        this.lastName = registrationDTO.getLastName();
        this.dateOfBirth = registrationDTO.getDateOfBirth();
        this.gender = registrationDTO.getGender();
        this.country = registrationDTO.getCountry();
        this.mobileNumber =registrationDTO.getMobileNumber();
        this.emailId = registrationDTO.getEmailId();
        this.address = registrationDTO.getAddress();
        this.userName = registrationDTO.getUserName();
        this.password = registrationDTO.getPassword();
        this.confirmPassword = registrationDTO.getConfirmPassword();
        this.userRole = registrationDTO.getUserRole();
    }
}
