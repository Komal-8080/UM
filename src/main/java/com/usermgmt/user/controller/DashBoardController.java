package com.usermgmt.user.controller;

import com.usermgmt.response.Response;
import com.usermgmt.response.ResponseDTO;
import com.usermgmt.user.dto.LatestRegistrationsDTO;
import com.usermgmt.user.dto.TopLocationsDTO;
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

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<ResponseDTO> getLatestRegistrations(@RequestHeader String token,@RequestParam String numberOfLatestRegistrations) {
        List<LatestRegistrationsDTO> response = iDashBoardService.getLatestRegistrations(token,numberOfLatestRegistrations);
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

    @ApiOperation("This API is used to get All Users count.This API is used to see number of registered users")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Users Count",
            response = Response.class
    )})
    @GetMapping("/getUserCount")
    public ResponseEntity<ResponseDTO> getUserCount(@RequestHeader String token) {
        Long response = iDashBoardService.getUserCount(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation("This API is used to get Top Locations.This API is used to get the locations where the count of the users is high")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Top Locations",
            response = Response.class
    )})
    @GetMapping("/getTopLocations")
    public ResponseEntity<ResponseDTO> getTopLocations(@RequestHeader String token,@RequestParam String numberOfTopLocations) {
        HashMap<String, Integer> response = iDashBoardService.getTopLocations(token,numberOfTopLocations);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation("This API is used to get Male And Female percentage.This API is used to get the percent of males and females registered")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting MaleFemalePercentage",
            response = Response.class
    )})
    @GetMapping("/getMaleFemalePercentage")
    public ResponseEntity<ResponseDTO> getMaleFemalePercentage(@RequestHeader String token,@RequestParam String gender) {
        Double response = iDashBoardService.getMaleFemalePercentage(token,gender);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation("This API is used to get Age.This API is used to get registered user age")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting User Age",
            response = Response.class
    )})
    @GetMapping("/getUserAgeList")
    public ResponseEntity<ResponseDTO> getUserAgeList(@RequestHeader String token, @RequestParam int minimumAge,@RequestParam int maximumAge) {
        int response = iDashBoardService.getUserAgeList(token,minimumAge,maximumAge);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation("This API is used to get Registration History.This API is used to get all time registration history according to year")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Registration History",
            response = Response.class
    )})
    @GetMapping("/getAllTimeRegistrationHistory")
    public ResponseEntity<ResponseDTO> getAllTimeRegistrationHistory(@RequestHeader String token) {
        HashMap<String,Long> response = iDashBoardService.getAllTimeRegistrationHistory(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
