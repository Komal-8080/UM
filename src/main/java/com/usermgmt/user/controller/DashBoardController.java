package com.usermgmt.user.controller;

import com.usermgmt.response.Response;
import com.usermgmt.response.ResponseDTO;
import com.usermgmt.user.dto.LatestRegistrationsDTO;
import com.usermgmt.user.dto.UserSummary;
import com.usermgmt.user.dto.UsersListSummary;
import com.usermgmt.user.service.IDashBoardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashBoard")
public class DashBoardController {

    @Autowired
    IDashBoardService iDashBoardService;

    @ApiOperation("This API is used to get Latest Registrations.This API is used to see  latest registration made where user name and registration time will be given")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Users List",
            response = Response.class
    )})
    @GetMapping("/getLatestRegistrations")
    public ResponseEntity<ResponseDTO> getLatestRegistrations(@RequestHeader String token) {
        List<LatestRegistrationsDTO> response = iDashBoardService.getLatestRegistrations(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation("This API is used to get All Active Users.This API is used to see Active Users from the list of users")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Users List",
            response = Response.class
    )})
    @GetMapping("/getActiveUser")
    public ResponseEntity<ResponseDTO> getActiveUser(@RequestHeader String token,@RequestParam String userStatus) {
        List<UserSummary> response = iDashBoardService.getActiveUser(token,userStatus);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
