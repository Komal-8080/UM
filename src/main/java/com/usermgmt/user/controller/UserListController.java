package com.usermgmt.user.controller;

import com.usermgmt.response.Response;
import com.usermgmt.response.ResponseDTO;
import com.usermgmt.user.dto.UserSummary;
import com.usermgmt.user.dto.UsersListSummary;
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

import java.util.List;

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
    public ResponseEntity<ResponseDTO> getUsersList(@RequestHeader String token, @RequestParam(defaultValue = "1") long usersList) {
        List<UsersListSummary> response = iUsersListService.getUsersList(token,usersList);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
