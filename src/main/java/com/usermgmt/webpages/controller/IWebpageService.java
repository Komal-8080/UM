package com.usermgmt.webpages.controller;

import com.usermgmt.user.model.UserPermissions;

public interface IWebpageService {

    UserPermissions getWebpagePermissions(String token, String webpageName);

}
