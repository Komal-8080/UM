package com.usermgmt.user.controller;

import com.usermgmt.response.Response;
import com.usermgmt.response.ResponseDTO;
import com.usermgmt.user.dto.BasicInformationDTO;
import com.usermgmt.user.dto.GeneralInformationDTO;
import com.usermgmt.user.dto.UserSummary;
import com.usermgmt.user.model.User;
import com.usermgmt.user.service.IProfile;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    IProfile iProfile;

    @ApiOperation("API used to Get Login History. This API is used to see user Login History\n" +
            " Whenever user log in the local date and time will get stored in the database and\n"+
            " using this API we can get the login history")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Login History",
            response = Response.class
    )})
    @GetMapping("/getLoginHistory")
    public ResponseEntity<ResponseDTO> getLoginHistory(@RequestHeader String token) {
        List<LocalDateTime> response = iProfile.getLoginHistory(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation("API used to Get Last Login. This API is used to see user Last Login\n" +
            " Here user can see last login time and date")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Last Login",
            response = Response.class
    )})
    @GetMapping("/getLastLogin")
    public ResponseEntity<ResponseDTO> getLastLogin(@RequestHeader String token) {
        LocalDateTime response = iProfile.getLastLogin(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation("API used to Get Basic Information. This API is used to see user Basic Information\n" +
            " Here user can view logged in user basic information like userName and Email")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Basic Information",
            response = Response.class
    )})
    @GetMapping("/getBasicInformation")
    public ResponseEntity<ResponseDTO> getBasicInformation(@RequestHeader String token) {
        BasicInformationDTO response = iProfile.getBasicInformation(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation("API used to Get General Information. This API is used to see user General Information\n" +
            " Here user can view logged in user General information like firstName,LastName,Gender etc")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ), @ApiResponse(
            code = 404,
            message = "Error getting Basic Information",
            response = Response.class
    )})
    @GetMapping("/getGeneralInformation")
    public ResponseEntity<ResponseDTO> getGeneralInformation(@RequestHeader String token) {
        GeneralInformationDTO response = iProfile.getGeneralInformation(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
