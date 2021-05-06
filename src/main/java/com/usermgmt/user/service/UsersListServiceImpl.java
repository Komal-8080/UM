package com.usermgmt.user.service;

import com.usermgmt.exceptions.UserException;
import com.usermgmt.user.dto.PermissionsDTO;
import com.usermgmt.user.dto.RegistrationDTO;
import com.usermgmt.user.dto.UsersListSummary;
import com.usermgmt.user.model.User;
import com.usermgmt.user.model.UserPermissions;
import com.usermgmt.user.repository.UserPermissionsRepository;
import com.usermgmt.user.repository.UserRepository;
import com.usermgmt.utility.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersListServiceImpl implements IUsersListService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPermissionsRepository userPermissionsRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Override
    public List<UsersListSummary> getUsersList(String token, long usersList) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            List<UsersListSummary> collect = userRepository.findAll().stream().map(users -> new UsersListSummary(users)).collect(Collectors.toList());
            if (collect.size() > 10) {
                return collect.subList(0, Math.toIntExact(usersList));
            }
            else
                return collect;
        }throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public User editUserDetails(String token, UUID userId, RegistrationDTO registrationDTO) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        Optional<User> byUserId = userRepository.findById(userId);
        if (isUserExists.isPresent() && byUserId.isPresent()) {
            byUserId.get().updateUserDetails(registrationDTO);
            return userRepository.save(isUserExists.get());
        }throw new UserException(UserException.ExceptionTypes.USER_NOT_FOUND);
    }

    @Override
    public void removeUserDetails(String token, UUID userId) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        Optional<User> byUserId = userRepository.findById(userId);
        if (isUserExists.isPresent() && byUserId.isPresent()) {
            userRepository.delete(byUserId.get());
        }
    }

    @Override
    public UserPermissions updatePermissions(String token, UUID permissionId, PermissionsDTO permissionsDTO) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        Optional<UserPermissions> byPermissionId = userPermissionsRepository.findByPermissionId(permissionId);
        if (isUserExists.isPresent() && byPermissionId.isPresent()) {
            byPermissionId.get().updatePermissions(permissionsDTO);
            userRepository.save(isUserExists.get());
            return userPermissionsRepository.save(byPermissionId.get());
        }throw new UserException(UserException.ExceptionTypes.USER_NOT_FOUND);
    }

    @Override
    public List<UsersListSummary> searchUser(String token, String search) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        List<UsersListSummary> collect = userRepository.findAll().stream().map(users -> new UsersListSummary(users)).collect(Collectors.toList());
        if (isUserExists.isPresent()) {
            return collect.stream().filter(usersListSummary -> (usersListSummary.getFirstName().equalsIgnoreCase(search)) || (usersListSummary.getEmailId().equalsIgnoreCase(search))).collect(Collectors.toList());
        } throw new UserException(UserException.ExceptionTypes.USER_NOT_FOUND);
    }
}
