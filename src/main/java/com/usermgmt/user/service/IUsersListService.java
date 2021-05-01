package com.usermgmt.user.service;

import com.usermgmt.user.dto.UsersListSummary;

import java.util.List;

public interface IUsersListService {

    List<UsersListSummary> getUsersList(String token, long usersList);
}
