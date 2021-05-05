package com.usermgmt.user.service;

import com.usermgmt.exceptions.UserException;
import com.usermgmt.user.dto.BasicInformationDTO;
import com.usermgmt.user.dto.GeneralInformationDTO;
import com.usermgmt.user.model.User;
import com.usermgmt.user.repository.UserRepository;
import com.usermgmt.utility.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProfileImpl implements IProfile{

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Override
    public List<LocalDateTime> getLoginHistory(String token) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()){
            List<LocalDateTime> loginList = isUserExists.get().getLoginHistory();
            List<LocalDateTime> reverseLoginList = new ArrayList<>();
            for (int i = loginList.size()-1; i >=0 ; --i) {
                LocalDateTime list = loginList.get(i);
                reverseLoginList.add(list);
            }
            return reverseLoginList;
        } throw new UserException(UserException.ExceptionTypes.USER_NOT_FOUND);
    }

    @Override
    public LocalDateTime getLastLogin(String token) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()){
            List<LocalDateTime> loginList = isUserExists.get().getLoginHistory();
            for (int i = loginList.size()-2; i >=0 ; --i) {
                return loginList.get(i);
            }
        } throw new UserException(UserException.ExceptionTypes.USER_NOT_FOUND);
    }

    @Override
    public BasicInformationDTO getBasicInformation(String token) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()){
            BasicInformationDTO basicInformationDTO = new BasicInformationDTO();
            basicInformationDTO.setEmailId(isUserExists.get().getEmailId());
            basicInformationDTO.setUserName(isUserExists.get().getUserName());
            return basicInformationDTO;
        }throw new UserException(UserException.ExceptionTypes.USER_NOT_FOUND);
    }

    @Override
    public GeneralInformationDTO getGeneralInformation(String token) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()){
            GeneralInformationDTO generalInformationDTO = new GeneralInformationDTO();
            generalInformationDTO.setFirstName(isUserExists.get().getFirstName());
            generalInformationDTO.setMiddleName(isUserExists.get().getMiddleName());
            generalInformationDTO.setLastName(isUserExists.get().getLastName());
            generalInformationDTO.setDateOfBirth(isUserExists.get().getDateOfBirth());
            generalInformationDTO.setGender(isUserExists.get().getGender());
            generalInformationDTO.setCountry(isUserExists.get().getCountry());
            generalInformationDTO.setMobileNumber(isUserExists.get().getMobileNumber());
            generalInformationDTO.setAddress(isUserExists.get().getAddress());
            return generalInformationDTO;
        }throw new UserException(UserException.ExceptionTypes.USER_NOT_FOUND);
    }
}
