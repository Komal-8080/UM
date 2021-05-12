package com.usermgmt.user.service;

import com.usermgmt.user.dto.LatestRegistrationsDTO;
import com.usermgmt.user.dto.UserSummary;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface IDashBoardService {

    List<LatestRegistrationsDTO> getLatestRegistrations(String token, String numberOfLatestRagistrations);

    List<UserSummary> getActiveUser(String token, String userStatus);

    Long getUserCount(String token);

    HashMap<String, Integer> getTopLocations(String token, String numberOfTopLocations);

    Double getMaleFemalePercentage(String token, String gender);

    int getUserAgeList(String token, int minimumAge, int maximumAge);
}
