package com.usermgmt.webpages.service;

import com.usermgmt.exceptions.UserException;
import com.usermgmt.user.model.User;
import com.usermgmt.user.model.UserPermissions;
import com.usermgmt.user.repository.UserPermissionsRepository;
import com.usermgmt.user.repository.UserRepository;
import com.usermgmt.utility.TokenUtil;
import com.usermgmt.webpages.controller.IWebpageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WebpageServiceImpl implements IWebpageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPermissionsRepository userPermissionsRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Override
    public UserPermissions getWebpagePermissions(String token, String webpageName) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            if (webpageName.equals("WebPage1")){
                return isUserExists.get().getWebPage1Permissions();
            }
            if (webpageName.equals("WebPage2")){
                return isUserExists.get().getWebPage2Permissions();
            }
            if (webpageName.equals("WebPage3")){
                return isUserExists.get().getWebPage3Permissions();
            }
        }throw new UserException(UserException.ExceptionTypes.USER_NOT_FOUND);
    }
}
