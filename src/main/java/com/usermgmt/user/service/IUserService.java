package com.usermgmt.user.service;

import com.usermgmt.response.ResponseToken;
import com.usermgmt.user.dto.*;
import com.usermgmt.user.model.User;
import com.usermgmt.user.model.UserPermissions;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {

    User userRegistration(RegistrationDTO registrationDTO);

    String emailValidation(String token);

    ResponseToken userLogin(UserDTO userDTO);

    String forgotPassword(EmailDTO emailDTO);

    String passwordReset(String token, PasswordDTO passwordDTO);

    String uploadProfileImage(String token, MultipartFile image) throws IOException;

    String getProfileImage(String token);

    void removeProfileImage(String token);

    UserPermissions setDashBoardPermissions(String token, PermissionsDTO permissionsDTO);

    UserPermissions setSettingsPermissions(String token, PermissionsDTO permissionsDTO);

    UserPermissions setWebPage3Permissions(String token, PermissionsDTO permissionsDTO);

    UserPermissions setWebPage2Permissions(String token, PermissionsDTO permissionsDTO);

    UserPermissions setWebPage1Permissions(String token, PermissionsDTO permissionsDTO);

    UserPermissions setUserInformationPermissions(String token, PermissionsDTO permissionsDTO);

    List<UserSummary> getRegistrations(String token, String user);
}
