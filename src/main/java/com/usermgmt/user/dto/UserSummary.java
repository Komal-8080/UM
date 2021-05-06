package com.usermgmt.user.dto;

import com.usermgmt.user.model.User;
import com.usermgmt.user.model.UserPermissions;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class UserSummary {

    private String id;
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
    private LocalDateTime loginDateTime;
    private boolean isVerify;
    private boolean rememberMe;
    private LocalDateTime registrationDate;
    private UserPermissions DashBoardPermissions;
    private UserPermissions SettingsPermissions;
    private UserPermissions UsersInformationPermissions;
    private UserPermissions WebPage1Permissions;
    private UserPermissions WebPage2Permissions;
    private UserPermissions WebPage3Permissions;
    private List<LocalDateTime> loginHistory;
    private boolean status;

    public UserSummary(User users) {
        this.id=users.getId().toString();
        this.firstName = users.getFirstName();
        this.middleName = users.getMiddleName();
        this.lastName = users.getLastName();
        this.dateOfBirth = users.getDateOfBirth();
        this.gender = users.getGender();
        this.country = users.getCountry();
        this.mobileNumber = users.getMobileNumber();
        this.emailId = users.getEmailId();
        this.address = users.getAddress();
        this.userName = users.getUserName();
        this.password = users.getPassword();
        this.confirmPassword = users.getConfirmPassword();
        this.userRole = users.getUserRole();
        this.profileImage = users.getProfileImage();
        this.loginDateTime = users.getLoginDateTime();
        this.isVerify = users.isVerify();
        this.rememberMe = users.isRememberMe();
        this.registrationDate = users.getRegistrationDate();
        this.DashBoardPermissions = users.getDashBoardPermissions();
        this.SettingsPermissions = users.getSettingsPermissions();
        this.UsersInformationPermissions = users.getUsersInformationPermissions();
        this.WebPage1Permissions = users.getWebPage1Permissions();
        this.WebPage2Permissions = users.getWebPage2Permissions();
        this.WebPage3Permissions = users.getWebPage3Permissions();
        this.loginHistory = users.getLoginHistory();
        this.status = users.isStatus();
    }
}
