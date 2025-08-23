package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.modeldto.UserDataDto;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;
import wmi.amu.edu.pl.pri.repositories.UserDataRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDataService {
    private final UserDataRepo userRepo;

    public UserDataDto getUserDataDto(Long id){
        return userRepo.findById(id).get().toUserDataDto();
    }

    public UserDataModel getUserData(Long id){
        return userRepo.getUserDataModelById(id).get();
    }

}
