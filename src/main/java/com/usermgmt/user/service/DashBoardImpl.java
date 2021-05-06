package com.usermgmt.user.service;

import com.usermgmt.exceptions.UserException;
import com.usermgmt.user.dto.LatestRegistrationsDTO;
import com.usermgmt.user.dto.UserSummary;
import com.usermgmt.user.model.User;
import com.usermgmt.user.repository.UserRepository;
import com.usermgmt.utility.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashBoardImpl implements IDashBoardService{

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<LatestRegistrationsDTO> getLatestRegistrations(String token) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            List<LatestRegistrationsDTO> collect = userRepository.findAll().stream().map(users -> new LatestRegistrationsDTO(users)).collect(Collectors.toList());
            List<LatestRegistrationsDTO> reverseList = new ArrayList<>();
            for (int i = collect.size()-1; i >=0 ; --i) {
                LatestRegistrationsDTO list = collect.get(i);
                reverseList.add(list);
            }
            if (reverseList.size() > 10) {
                return collect.subList(0,9);
            } else
                return reverseList;
        }throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public List<UserSummary> getActiveUser(String token, String userStatus) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            List<UserSummary> collect = userRepository.findAll().stream().map(users -> new UserSummary(users)).collect(Collectors.toList());
            if (userStatus.equalsIgnoreCase("active"))
                return collect.stream().filter(users -> (users.isStatus())).collect(Collectors.toList());
            if (userStatus.equalsIgnoreCase("inactive"))
                return collect.stream().filter(users -> (!users.isStatus())).collect(Collectors.toList());
        }throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }
}

