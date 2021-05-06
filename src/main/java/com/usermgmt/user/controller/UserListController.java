package com.usermgmt.user.controller;

import com.usermgmt.response.Response;
import com.usermgmt.response.ResponseDTO;
import com.usermgmt.user.dto.PermissionsDTO;
import com.usermgmt.user.dto.RegistrationDTO;
import com.usermgmt.user.dto.UserSummary;
import com.usermgmt.user.dto.UsersListSummary;
import com.usermgmt.user.model.User;
import com.usermgmt.user.model.UserPermissions;
import com.usermgmt.user.repository.UserRepository;
import com.usermgmt.user.service.IUserService;
import com.usermgmt.user.service.IUsersListService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/userList")
public class UserListController {

    @Autowired
    IUsersListService iUsersListService;

    @Autowired
    UserRepository userRepository;

    @ApiOperation("API used to Get UserList. This API is used to see List of Users\n")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Users List",
            response = Response.class
    )})
    @GetMapping("/getUsersList")
    public ResponseEntity<ResponseDTO> getUsersList(@RequestHeader String token, @RequestParam(defaultValue = "10") long usersList) {
        List<UsersListSummary> response = iUsersListService.getUsersList(token,usersList);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }


    @ApiOperation("API used to Edit User Details. This API is used to Edit User Details\n" +
            "User Details can be edited by passing User Id and edited details will be saved in the database")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "User Details edited Successfully",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error Editing User Details",
            response = Response.class
    )})
    @PutMapping("/editUserDetails/{userId}")
    public ResponseEntity<ResponseDTO> editUserDetails(@RequestHeader String token,@PathVariable UUID userId,@Valid @RequestBody RegistrationDTO registrationDTO) {
        User editedUser = iUsersListService.editUserDetails(token,userId,registrationDTO);
        ResponseDTO responseDTO = new ResponseDTO("User Details Edited Successfully", editedUser);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation("API used to Delete User Details. This API is used delete unwanted User Details permanently \n" +
            "User Details can be deleted permanently by UserId and deleted details cannot be restored")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Notes deleted Successfully",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error deleting Notes",
            response = Response.class
    )})
    @DeleteMapping("/deleteUserDetails/{userId}")
    public ResponseEntity<ResponseDTO> removeUserDetails(@RequestHeader String token,@PathVariable UUID userId) {
        iUsersListService.removeUserDetails(token,userId);
        ResponseDTO responseDTO = new ResponseDTO("Deleting User Details..","User Details Deleted Successfully" );
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation("API Used for updating Permissions.This API is used for updating Permissions\n"+
            "user has to set true if the permissions is given else permissions will be false when permission id is passed")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Permissions Set Successfully",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Error setting Permissions",
            response = Response.class
    )})
    @PutMapping("/updatePermissions")
    public ResponseEntity<ResponseDTO> updatePermissions(@RequestHeader String token , @RequestParam UUID permissionId,@RequestBody PermissionsDTO permissionsDTO) {
        UserPermissions response = iUsersListService.updatePermissions(token,permissionId,permissionsDTO);
        ResponseDTO responseDTO = new ResponseDTO("Permissions Set Successfully", response);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    @ApiOperation("This API is used tro search user.This API is used to Get User when searched by User First Name or Email Id.\n")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Users List",
            response = Response.class
    )})
    @GetMapping("/searchUser")
    public ResponseEntity<ResponseDTO> searchUser(@RequestHeader String token, @RequestParam String search) {
        List<UsersListSummary> response = iUsersListService.searchUser(token,search);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
