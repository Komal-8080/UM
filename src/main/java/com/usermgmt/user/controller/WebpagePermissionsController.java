package com.usermgmt.user.controller;

import com.usermgmt.response.Response;
import com.usermgmt.response.ResponseDTO;
import com.usermgmt.user.model.UserPermissions;
import com.usermgmt.user.service.IWebpageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webpages")
public class WebpagePermissionsController {

    @Autowired
    IWebpageService iWebPageService;

    @ApiOperation("API Used for Getting Webpage Permissions.This API is used to view Webpage Permissions\n"+
            "user has to see Webpage permissions by passing Webpage name")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Get Call Successful",
            response = Response.class
    ),@ApiResponse(
            code = 404,
            message = "Error Getting Permissions",
            response = Response.class
    )})
    @GetMapping("/getWebpagePermissions")
    public ResponseEntity<ResponseDTO> getWebpagePermissions(@RequestHeader String token , @RequestParam String webpageName) {
        UserPermissions response = iWebPageService.getWebpagePermissions(token,webpageName);
        ResponseDTO responseDTO = new ResponseDTO("Permissions granted for "+webpageName, response);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

}
