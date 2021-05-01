package com.usermgmt.user.service;

import com.usermgmt.configuration.ApplicationConfiguration;
import com.usermgmt.exceptions.UserException;
import com.usermgmt.response.ResponseToken;
import com.usermgmt.user.dto.*;
import com.usermgmt.user.model.User;
import com.usermgmt.user.model.UserPermissions;
import com.usermgmt.user.repository.UserPermissionsRepository;
import com.usermgmt.user.repository.UserRepository;
import com.usermgmt.utility.FileUploadUtil;
import com.usermgmt.utility.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPermissionsRepository userPermissionsRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    EmailService emailService;

    @Override
    public User userRegistration(RegistrationDTO registrationDTO) {
        Optional<User> userEmailId = userRepository.findByUserName(registrationDTO.getUserName());
        if (!userEmailId.isPresent()) {
            User registration = new User(registrationDTO);
            boolean status = (registrationDTO.getPassword().equalsIgnoreCase(registrationDTO.getConfirmPassword()));
            if( status == true ) {
                registration.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
                registration.setConfirmPassword(passwordEncoder.encode(registrationDTO.getConfirmPassword()));
                User saveUser = userRepository.save(registration);
                UUID id = registration.getId();
                String emailId = registrationDTO.getEmailId();
                String url = emailService.getUrl(id)+"/valid";
                emailService.sendMail(emailId,"Email Validation","Dear "+registration.getFirstName()+"\n"+"Kindly,Click on the below link to validate email id"+"\n"+url);
                return userRepository.save(registration);
            }throw new UserException(UserException.ExceptionTypes.PASSWORD_DOES_NOT_MATCH);
        } throw new UserException(UserException.ExceptionTypes.USER_EXISTS);
    }

    @Override
    public String emailValidation(String token) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()){
            isUserExists.get().setVerify(true);
            userRepository.save(isUserExists.get());
            return ApplicationConfiguration.getMessageAccessor().getMessage("101");
        } throw new UserException(UserException.ExceptionTypes.USER_NOT_FOUND);
    }

    @Override
    public ResponseToken userLogin(UserDTO userDTO) {
        Optional<User> userData = userRepository.findByUserName(userDTO.getUserName());
        if (userData.isPresent()) {
            return authentication(userData,userDTO.getPassword());
        } throw new UserException(UserException.ExceptionTypes.INVALID_USERNAME_OR_PASSWORD);
    }

    private ResponseToken authentication(Optional<User> user, String password) {
        ResponseToken response = new ResponseToken();
        boolean status = passwordEncoder.matches(password, user.get().getPassword());
        if (status == true) {
            String token = tokenUtil.createToken(user.get().getId());
            user.get().setLastLoginDateTime(user.get().getLoginDateTime());
            user.get().setLoginDateTime(LocalDateTime.now());
            response.setToken(token);
            response.setStatusCode(200);
            response.setStatusMessage(ApplicationConfiguration.getMessageAccessor().getMessage("102"));
            return response;
        } throw new UserException(UserException.ExceptionTypes.INVALID_USERNAME_OR_PASSWORD);
    }

    @Override
    public String forgotPassword(EmailDTO emailDTO) {
        Optional<User> userEmailId = userRepository.findByEmailId(emailDTO.getEmailId());
        if (userEmailId.isPresent()) {
            UUID id = userEmailId.get().getId();
            String emailId = emailDTO.getEmailId();
            String url = emailService.getPasswordResetUrl(id);
            emailService.sendMail(emailId,"Regarding Password Reset","Dear "+userEmailId.get().getFirstName()+"\n"+"Kindly,Click on the below link to Reset Password"+"\n"+url);
            return emailId ;
        } throw new UserException(UserException.ExceptionTypes.EMAIL_NOT_FOUND);
    }

    @Override
    public String passwordReset(String token, PasswordDTO passwordDTO) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            boolean status = (passwordDTO.getNewPassword().equalsIgnoreCase(passwordDTO.getConfirmPassword()));
            if( status == true ) {
                isUserExists.get().setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
                userRepository.save(isUserExists.get());
                return ApplicationConfiguration.getMessageAccessor().getMessage("103");
            } throw new UserException(UserException.ExceptionTypes.PASSWORD_DOES_NOT_MATCH);
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public String uploadProfileImage(String token, MultipartFile image) throws IOException {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            isUserExists.get().setProfileImage(fileName);
            User saveImage = userRepository.save(isUserExists.get());
            String uploadDir = "profileImages/" + saveImage.getProfileImage();
            FileUploadUtil.saveFile(uploadDir, fileName, image);
            return ApplicationConfiguration.getMessageAccessor().getMessage("104");
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public String getProfileImage(String token) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()){
            String profileImage = isUserExists.get().getProfileImage();
            return profileImage;
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);

    }

    @Override
    public void removeProfileImage(String token) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            isUserExists.get().setProfileImage(null);
            userRepository.save(isUserExists.get());
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public UserPermissions setDashBoardPermissions(String token, PermissionsDTO permissionsDTO) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            UserPermissions permissions = new UserPermissions(permissionsDTO);
            permissions.setId(id);
            isUserExists.get().setDashBoardPermissions(permissions);
            userRepository.save(isUserExists.get());
            return userPermissionsRepository.save(permissions) ;
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public UserPermissions setSettingsPermissions(String token, PermissionsDTO permissionsDTO) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            UserPermissions permissions = new UserPermissions(permissionsDTO);
            permissions.setId(id);
            isUserExists.get().setSettingsPermissions(permissions);
            userRepository.save(isUserExists.get());
            return userPermissionsRepository.save(permissions) ;
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public UserPermissions setWebPage3Permissions(String token, PermissionsDTO permissionsDTO) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            UserPermissions permissions = new UserPermissions(permissionsDTO);
            permissions.setId(id);
            isUserExists.get().setWebPage3Permissions(permissions);
            userRepository.save(isUserExists.get());
            return userPermissionsRepository.save(permissions) ;
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public UserPermissions setWebPage2Permissions(String token, PermissionsDTO permissionsDTO) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            UserPermissions permissions = new UserPermissions(permissionsDTO);
            permissions.setId(id);
            isUserExists.get().setWebPage2Permissions(permissions);
            userRepository.save(isUserExists.get());
            return userPermissionsRepository.save(permissions);
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public UserPermissions setWebPage1Permissions(String token, PermissionsDTO permissionsDTO) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            UserPermissions permissions = new UserPermissions(permissionsDTO);
            permissions.setId(id);
            isUserExists.get().setWebPage1Permissions(permissions);
            userRepository.save(isUserExists.get());
            return userPermissionsRepository.save(permissions) ;
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public UserPermissions setUserInformationPermissions(String token, PermissionsDTO permissionsDTO) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            UserPermissions permissions = new UserPermissions(permissionsDTO);
            permissions.setId(id);
            isUserExists.get().setUsersInformationPermissions(permissions);
            userRepository.save(isUserExists.get());
            return userPermissionsRepository.save(permissions) ;
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public List<UserSummary> getRegistrations(String token, String user) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            if (user.equalsIgnoreCase("all")) {
                return userRepository.findAll().stream().map(users -> new UserSummary(users)).collect(Collectors.toList());
            }
            if (isUserExists.isPresent()){
                return Collections.singletonList(new UserSummary(isUserExists.get()));
            } throw new UserException(UserException.ExceptionTypes.USER_NOT_FOUND);
        }throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }
}
