package com.usermgmt.user.service;

import com.usermgmt.exceptions.UserException;
import com.usermgmt.user.dto.UserSummary;
import com.usermgmt.user.dto.UsersListSummary;
import com.usermgmt.user.model.User;
import com.usermgmt.user.repository.UserRepository;
import com.usermgmt.utility.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersListServiceImpl implements IUsersListService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Override
    public List<UsersListSummary> getUsersList(String token, long usersList) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {

            List<UsersListSummary> collect = userRepository.findAll().stream().map(users -> new UsersListSummary(users)).collect(Collectors.toList());
           return collect.subList(0, Math.toIntExact(usersList));


//            }
        }throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }
}
