package com.usermgmt.user.service;

import com.usermgmt.user.dto.BasicInformationDTO;
import com.usermgmt.user.dto.GeneralInformationDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IProfile {

    List<LocalDateTime> getLoginHistory(String token);

    LocalDateTime getLastLogin(String token);

    BasicInformationDTO getBasicInformation(String token);

    GeneralInformationDTO getGeneralInformation(String token);
}
