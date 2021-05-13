package com.usermgmt.user.service;

import com.usermgmt.exceptions.UserException;
import com.usermgmt.user.dto.LatestRegistrationsDTO;
import com.usermgmt.user.dto.UserSummary;
import com.usermgmt.user.model.User;
import com.usermgmt.user.repository.UserRepository;
import com.usermgmt.utility.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashBoardImpl implements IDashBoardService{

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<LatestRegistrationsDTO> getLatestRegistrations(String token, String numberOfLatestRegistrations) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            List<LatestRegistrationsDTO> collect = userRepository.findAll().stream().map(users -> new LatestRegistrationsDTO(users)).collect(Collectors.toList());
            List<LatestRegistrationsDTO> reverseList = new ArrayList<>();
            for (int i = collect.size()-1; i >=0 ; --i) {
                LatestRegistrationsDTO list = collect.get(i);
                reverseList.add(list);
            }
            if (numberOfLatestRegistrations.equals("default")) {
                if (reverseList.size() > 10)
                    return reverseList.subList(0,9);
                else return reverseList;
            }else if (numberOfLatestRegistrations.equals("all") && reverseList.size() > 10)
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
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public Long getUserCount(String token) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            List<UserSummary> collect = userRepository.findAll().stream().map(users -> new UserSummary(users)).collect(Collectors.toList());
            return collect.stream().count();
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public HashMap<String, Integer> getTopLocations(String token, String numberOfTopLocations) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            List<UserSummary> collect = userRepository.findAll().stream().map(users -> new UserSummary(users)).collect(Collectors.toList());
            HashMap<String, Integer> topLocations = new HashMap<>();
            for (int i = 0; i <= collect.size() - 1; i++) {
                int userCount = 1;
                for (int j = 0; j < i; j++) {
                    if (collect.get(i).getCountry().equals(collect.get(j).getCountry()))
                        userCount += 1;
                }
                topLocations.put(collect.get(i).getCountry(), userCount);
            }
            LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
            topLocations.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
            return reverseSortedMap;
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public Double getMaleFemalePercentage(String token, String gender) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            List<UserSummary> collect = userRepository.findAll().stream().map(users -> new UserSummary(users)).collect(Collectors.toList());
            double totalNumberOfMale = (double) collect.stream().filter(userSummary -> userSummary.getGender().equalsIgnoreCase("male")).collect(Collectors.toList()).stream().count();
            double totalNumberOfFemale = (double) collect.stream().filter(userSummary -> userSummary.getGender().equalsIgnoreCase("female")).collect(Collectors.toList()).stream().count();
            double total = (double) collect.stream().count();
            if ( gender.equalsIgnoreCase("male")) {
                double malePercentage = totalNumberOfMale/total*100;
                return malePercentage;
            }
            if ( gender.equalsIgnoreCase("female")) {
                double feMalePercentage = totalNumberOfFemale/total*100;
                return feMalePercentage;
            }
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public int getUserAgeList(String token, int minimumAge, int maximumAge) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            List<UserSummary> collect = userRepository.findAll().stream().map(users -> new UserSummary(users)).collect(Collectors.toList());
            List<Integer> byAge = collect.stream().map(users -> users.getAge()).collect(Collectors.toList());
            List<Integer> newList = new ArrayList<>();
            for ( int i : byAge) {
                if (i >= minimumAge && i <= maximumAge) {
                    newList.add(i);
                }
            } return (int) newList.stream().count();
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

    @Override
    public HashMap<String, Long> getAllTimeRegistrationHistory(String token) {
        UUID id = UUID.fromString(tokenUtil.decodeToken(token));
        Optional<User> isUserExists = userRepository.findById(id);
        if (isUserExists.isPresent()) {
            List<UserSummary> collect = userRepository.findAll().stream().map(users -> new UserSummary(users)).collect(Collectors.toList());
            HashMap<String, Long> registrationHistory = new HashMap<>();
            for (int i = 0; i <= collect.size() - 1; i++) {
                long userCount = 1;
                for (int j = 0; j < i; j++) {
                    if (collect.get(i).getRegistrationDate().getYear() == collect.get(j).getRegistrationDate().getYear())
                        userCount += 1;
                }
                registrationHistory.put(String.valueOf(collect.get(i).getRegistrationDate().getYear()), userCount);
            }
            return registrationHistory;
        } throw new UserException(UserException.ExceptionTypes.INVAlID_TOKEN);
    }

}

