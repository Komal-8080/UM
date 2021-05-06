package com.usermgmt.user.service;

import com.usermgmt.user.dto.LatestRegistrationsDTO;
import com.usermgmt.user.dto.UserSummary;
import java.util.List;

public interface IDashBoardService {

    List<LatestRegistrationsDTO> getLatestRegistrations(String token);

    List<UserSummary> getActiveUser(String token, String userStatus);
}
