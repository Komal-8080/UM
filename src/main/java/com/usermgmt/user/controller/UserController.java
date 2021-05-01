package com.usermgmt.user.controller;

import com.usermgmt.response.Response;
import com.usermgmt.response.ResponseDTO;
import com.usermgmt.response.ResponseToken;
import com.usermgmt.user.dto.*;
import com.usermgmt.user.model.User;
import com.usermgmt.user.model.UserPermissions;
import com.usermgmt.user.repository.UserRepository;
import com.usermgmt.user.service.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    @Autowired
    UserRepository userRepository;

    @ApiOperation("API used to register User. This API is used to\n" +
            "register user with basic details. If user does not exist with given\n" +
            "email id then user has to validate email id by clicking on the link sent in mail")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Click on the link sent in email for validation",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Invalid Data Entered",
            response = Response.class
    )})
    @PostMapping("/registration")
    public ResponseEntity<ResponseDTO> userRegistration(@Valid @RequestBody RegistrationDTO registrationDTO) {
        User user = iUserService.userRegistration(registrationDTO);
        ResponseDTO responseDTO = new ResponseDTO("Go to mail and click on link to validate email id", user);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }


    @ApiOperation("API used to Validate User and Save Details in Database. This API is used to\n" +
            "validate user with given email id. If user email id is valid then user registration will be successful")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "User Registered Successfully",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Validations Error message",
            response = Response.class
    )})
    @GetMapping(value = "/{token}/valid")
    public ResponseEntity<ResponseDTO> emailValidation(@PathVariable String token) {
        String registration = iUserService.emailValidation(token);
        ResponseDTO responseDTO = new ResponseDTO(registration,"User Registered Successfully");
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }


    @ApiOperation("API Used to for User Login.This API is used for user login\n"+
                    "if userId and password are valid then user will be given a token for further operations\n"+
                    "on successful login")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "User login Successful",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Invalid UserId or Password",
            response = Response.class
    )})
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody UserDTO userDTO) {
        ResponseToken userData = iUserService.userLogin(userDTO);
        ResponseDTO responseDTO = new ResponseDTO("User Logged in Successfully", userData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }


    @ApiOperation("API Used if User ForgotPassword.This API is used if user forgot password\n"+
            "user has to provide email id on which mail will be sent and user has to click on the provided link in the mail\n"+
            "to reset password.if the email is valid then user will be diverted to password reset api")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "link for password reset",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "invalid data entered",
            response = Response.class
    )})
    @PostMapping("/forgotPassword")
    public ResponseEntity<ResponseDTO> forgotPassword(@Valid @RequestBody EmailDTO emailDTO) {
        String userEmail = iUserService.forgotPassword(emailDTO);
        ResponseDTO responseDTO = new ResponseDTO("Go to mail and click on link to reset password", userEmail);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }


    @ApiOperation("API Used for Password Reset.This API is used for setting password\n"+
                    "user has to enter new password and also need to confirm the same and if new password and confirm password matches\n"+
                    "then new password will be set and user can use the same password for further operations")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Password reset Successful",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "New Password and Confirm Password does not match",
            response = Response.class
    )})
    @PostMapping(value = "/passwordReset/{token}")
    public ResponseEntity<ResponseDTO> passwordReset(@PathVariable String token,@Valid @RequestBody PasswordDTO passwordDTO) {
        String resetData = iUserService.passwordReset(token,passwordDTO);
        ResponseDTO responseDTO = new ResponseDTO("Password Reset Successfully", resetData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }


    @ApiOperation("API Used for Setting Profile Image.This API is used for Setting Profile Image\n"+
            "user has to select the image from system storage and the same image will be set to profile image")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Profile Image Set Successfully",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Error Reading File",
            response = Response.class
    )})
    @PostMapping("/uploadProfileImage")
    public ResponseEntity<ResponseDTO> uploadProfileImage(@RequestHeader String token , @RequestParam MultipartFile image) throws IOException {
        String response = iUserService.uploadProfileImage(token, image);
        ResponseDTO responseDTO = new ResponseDTO("Profile Image Uploading..", response);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }


    @ApiOperation("API to See Profile Image.This API is used for getting Profile Image\n"+
            "user can see the profile image here")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Error Reading File",
            response = Response.class
    )})
    @PostMapping("/getProfileImage")
    public ResponseEntity<ResponseDTO> getProfileImage(@RequestHeader String token ) {
        String response = iUserService.getProfileImage(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }


    @ApiOperation("API Used for Removing Profile Image.This API is used for removing Profile Image\n"+
            "user can remove the profile image ")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Profile Image removed Successfully",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Error Removing File",
            response = Response.class
    )})
    @DeleteMapping("/removeProfileImage")
    public ResponseEntity<ResponseDTO> removeProfileImage(@RequestHeader String token) {
        iUserService.removeProfileImage(token);
        ResponseDTO responseDTO = new ResponseDTO("Removing profile Image","image removed Successfully");
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    @ApiOperation("API Used for Setting DashBoard Permissions.This API is used for Setting DashBoard Permissions\n"+
            "user has to set true if the permissions is given else permissions will be false")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Permissions Set Successfully",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Error setting Permissions",
            response = Response.class
    )})
    @PostMapping("/setDashBoardPermissions")
    public ResponseEntity<ResponseDTO> setDashBoardPermissions(@RequestHeader String token , @RequestBody PermissionsDTO permissionsDTO) {
        UserPermissions response = iUserService.setDashBoardPermissions(token, permissionsDTO);
        ResponseDTO responseDTO = new ResponseDTO("Permissions Set Successfully", response);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    @ApiOperation("API Used for Setting Settings Permissions.This API is used for Setting Settings Permissions\n"+
            "user has to set true if the permissions is given else permissions will be false")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Permissions Set Successfully",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Error setting Permissions",
            response = Response.class
    )})
    @PostMapping("/setSettingsPermissions")
    public ResponseEntity<ResponseDTO> setSettingsPermissions(@RequestHeader String token , @RequestBody PermissionsDTO permissionsDTO) {
        UserPermissions response = iUserService.setSettingsPermissions(token, permissionsDTO);
        ResponseDTO responseDTO = new ResponseDTO("Permissions Set Successfully", response);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    @ApiOperation("API Used for Setting User Information Permissions.This API is used for Setting User Information Permissions\n"+
            "user has to set true if the permissions is given else permissions will be false")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Permissions Set Successfully",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Error setting Permissions",
            response = Response.class
    )})
    @PostMapping("/setUserInformationPermissions")
    public ResponseEntity<ResponseDTO> setUserInformationPermissions(@RequestHeader String token , @RequestBody PermissionsDTO permissionsDTO) {
        UserPermissions response = iUserService.setUserInformationPermissions(token, permissionsDTO);
        ResponseDTO responseDTO = new ResponseDTO("Permissions Set Successfully", response);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    @ApiOperation("API Used for Setting WebPage1 Permissions.This API is used for Setting WebPage1 Permissions\n"+
            "user has to set true if the permissions is given else permissions will be false")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Permissions Set Successfully",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Error setting Permissions",
            response = Response.class
    )})
    @PostMapping("/setWebPage1Permissions")
    public ResponseEntity<ResponseDTO> setWebPage1Permissions(@RequestHeader String token , @RequestBody PermissionsDTO permissionsDTO) {
        UserPermissions response = iUserService.setWebPage1Permissions(token, permissionsDTO);
        ResponseDTO responseDTO = new ResponseDTO("Permissions Set Successfully", response);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    @ApiOperation("API Used for Setting WebPage2 Permissions.This API is used for Setting WebPage2 Permissions\n"+
            "user has to set true if the permissions is given else permissions will be false")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Permissions Set Successfully",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Error setting Permissions",
            response = Response.class
    )})
    @PostMapping("/setWebPage2Permissions")
    public ResponseEntity<ResponseDTO> setWebPage2Permissions(@RequestHeader String token , @RequestBody PermissionsDTO permissionsDTO) {
        UserPermissions response = iUserService.setWebPage2Permissions(token, permissionsDTO);
        ResponseDTO responseDTO = new ResponseDTO("Permissions Set Successfully", response);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }


    @ApiOperation("API Used for Setting WebPage3 Permissions.This API is used for Setting WebPage3 Permissions\n"+
            "user has to set true if the permissions is given else permissions will be false")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Permissions Set Successfully",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Error setting Permissions",
            response = Response.class
    )})
    @PostMapping("/setWebPage3Permissions")
    public ResponseEntity<ResponseDTO> setWebPage3Permissions(@RequestHeader String token , @RequestBody PermissionsDTO permissionsDTO) {
        UserPermissions response = iUserService.setWebPage3Permissions(token, permissionsDTO);
        ResponseDTO responseDTO = new ResponseDTO("Permissions Set Successfully", response);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    @ApiOperation("API used to Get Registrations. This API is used to see List of Registrations\n" +
            "if all is passed then user can view all registrations and if a User Id is passed then\n"+
            "user can see single User Details")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Notes",
            response = Response.class
    )})
    @GetMapping("/getRegistrations")
    public ResponseEntity<ResponseDTO> getRegistrations(@RequestHeader String token,@RequestParam String user) {
        List<UserSummary> response = iUserService.getRegistrations(token,user);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }
}
